package org.langapp.documents.component;

import org.langapp.documents.dto.processor.*;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.documents.dto.processor.selection.WordSelection;
import org.langapp.string.StringUtil;
import org.langapp.translations.dto.Translation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.IntStream;

final public class Gatherers {

    public record WordMapperState(AtomicInteger atomicInteger,
                                  Map<String, Translation> translatedPhrases) implements StringUtil {
        boolean isPhrasePart(String word) {
            return translatedPhrases.values().stream()
                    .anyMatch(phrase -> phrase
                            .fromLangDetails().content()
                            .contains(word));
        }
    }
    public Gatherer<String, WordMapperState, Word> mapWords(Map<String, Translation> translatedPhrases) {
        Supplier<WordMapperState> accumulator = () -> new WordMapperState(new AtomicInteger(), translatedPhrases);
        Gatherer.Integrator<WordMapperState, String, Word> integrator = Gatherer.Integrator.of((state, wordStr, downstream) -> {
            String normalizedWord = state.normalizeString(wordStr);
            var unit = (Word) (normalizedWord.isBlank() ?
                    new InvalidWord(state.atomicInteger().getAndIncrement(), wordStr, normalizedWord) :
                    state.isPhrasePart(normalizedWord) ?
                    new PhrasePart(state.atomicInteger().getAndIncrement(), wordStr, normalizedWord) :
                    new Standalone(state.atomicInteger().getAndIncrement(), wordStr, normalizedWord));
            return downstream.push(unit);
        });
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public record PhraseAllocationState(Map<String, Translation> translatedPhrases,
                                        ArrayList<Word> buffer) implements StringUtil {
        Optional<Translation> extractPhrase(String phraseValue) {
            return translatedPhrases.values().stream()
                    .filter(translation -> translation.fromLangDetails().content()
                            .trim()
                            .equalsIgnoreCase(phraseValue))
                    .findFirst();
        }
    }
    public Gatherer<Word, PhraseAllocationState, Unit> allocatePhrases(Map<String, Translation> translatedPhrases) {
        Supplier<PhraseAllocationState> accumulator = () -> new PhraseAllocationState(translatedPhrases, new ArrayList<>());
        Gatherer.Integrator<PhraseAllocationState, Word, Unit> integrator = Gatherer.Integrator.of((state, unit, downstream) -> {
            if(unit instanceof PhrasePart part) {
                state.buffer().add(part);
                var bufferValue = String.join(" ", state.buffer().stream()
                        .map(_unit -> state.normalizeString(_unit.rawContent()))
                        .toList());
                var optPhrase = state.extractPhrase(bufferValue);
                if(optPhrase.isPresent()) {
                    var phraseTranslation = optPhrase.get();

                    var id = state.buffer().getFirst().id();
                    var rawContent = String.join(" ", state.buffer().stream()
                            .map(Unit::rawContent)
                            .toList());
                    var size = state.buffer().size();
                    var content = state.normalizeString(rawContent);
                    var phrase = new TranslatedPhrase(id, size, rawContent, content, phraseTranslation);
                    downstream.push(phrase);
                    state.buffer().clear();
                }
            }
            else {
                state.buffer().forEach(downstream::push);
                downstream.push(unit);
                state.buffer().clear();
            }
            return !downstream.isRejecting();
        });
        BiConsumer<PhraseAllocationState, Gatherer.Downstream<? super Unit>> finisher = (state, downstream) ->
                state.buffer().forEach(downstream::push);
        return Gatherer.ofSequential(accumulator, integrator, finisher);
    }

    public record WordTranslationsState(Map<String, Translation> translatedWords) implements StringUtil {
        public Optional<Translation> extractTranslation(String wordValue) {
            return Optional.ofNullable(translatedWords.get(wordValue));
        }
    }
    public Gatherer<Unit, WordTranslationsState, Unit> mapWordTranslations(Map<String, Translation> translatedWords) {
        Supplier<WordTranslationsState> accumulator = () -> new WordTranslationsState(translatedWords);
        Gatherer.Integrator<WordTranslationsState, Unit, Unit> integrator = Gatherer.Integrator.of((state, unit, downstream) ->
                downstream.push(switch (unit) {
                    case Phrase phrase -> phrase;
                    case Word word -> {
                        String normalizedWord = state.normalizeString(word.rawContent());
                        yield state.extractTranslation(normalizedWord)
                                .map(translation -> (Unit) new TranslatedWord(word.id(), word.rawContent(), state.normalizeString(word.rawContent()), translation))
                                .orElse(normalizedWord.isBlank() ?
                                        unit :
                                        new NewWord(word.id(), word.rawContent(), state.normalizeString(word.rawContent())));
                    }
                }));
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public Gatherer<Unit, ?, Unit> allocateSelectedWord(WordSelection details) {
        Objects.requireNonNull(details);
        Gatherer.Integrator<Void, Unit, Unit> integrator = ((_, unit, downstream) ->
                unit.id() == details.wordId() ?
                        downstream.push(new SelectedWord(unit.id(), unit.rawContent(), unit.content())) :
                        downstream.push(unit)
        );
        return Gatherer.ofSequential(integrator);
    }

    public record SelectedPhraseAllocationState(ArrayList<Word> buffer) implements StringUtil {
        public boolean isSelectedPhrasePart(String phraseValue, String wordValue) {
            return phraseValue.contains(wordValue);
        }
        public boolean inRange(int wordId, IntStream range) {
            return range.anyMatch(id -> id == wordId);
        }
    }
    public Gatherer<Unit, SelectedPhraseAllocationState, Unit> allocateSelectedPhrase(PhraseSelection details) {
        Objects.requireNonNull(details);
        Supplier<SelectedPhraseAllocationState> accumulator = () -> new SelectedPhraseAllocationState(new ArrayList<>());
        Gatherer.Integrator<SelectedPhraseAllocationState, Unit, Unit> integrator = Gatherer.Integrator.of((state, element, downstream) -> {
            switch (element) {
                case Phrase phrase -> {
                    return phrase.id() == details.startId() ?
                            downstream.push(new SelectedPhrase(phrase.id(), phrase.rawContent(), phrase.content())) :
                            downstream.push(phrase);
                }
                case Word word -> {
                    if(state.inRange(word.id(), IntStream.range(details.startId(), details.endId() + 1))) {
                        state.buffer().add(word);
                        if(state.buffer().size() == details.endId() - details.startId() + 1) {
                            var id = state.buffer().getFirst().id();
                            var rawContent = String.join(" ", state.buffer().stream()
                                    .map(Unit::rawContent)
                                    .toList());
                            var content = state.normalizeString(rawContent);
                            var phrase = (Unit) (new SelectedPhrase(id, rawContent, content));
                            downstream.push(phrase);
                            state.buffer().clear();
                        }
                    }
                    else {
                        var selectedPhraseValue = state.normalizeString(details.rawContent());
                        boolean isSelectedPhrasePart = state.isSelectedPhrasePart(selectedPhraseValue, word.rawContent());
                        if(isSelectedPhrasePart) {
                            state.buffer().add(word);
                            var bufferValue = String.join(" ", state.buffer().stream()
                                    .map(_unit -> state.normalizeString(_unit.rawContent()))
                                    .toList());
                            if(bufferValue.equals(selectedPhraseValue)) {
                                var id = state.buffer().getFirst().id();
                                var rawContent = String.join(" ", state.buffer().stream()
                                        .map(Unit::rawContent)
                                        .toList());
                                var size = state.buffer().size();
                                var content = state.normalizeString(rawContent);
                                var phrase = (Unit) (id == details.startId() ?
                                        new SelectedPhrase(id, rawContent, content) :
                                        new NewPhrase(id, size, rawContent, content));
                                downstream.push(phrase);
                                state.buffer().clear();
                            }
                        }
                        else {
                            state.buffer().forEach(downstream::push);
                            downstream.push(word);
                            state.buffer().clear();
                        }
                    }
                }
            }
            return !downstream.isRejecting();
        });
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public Gatherer<Unit, ?, Unit> doNothing() {
        Gatherer.Integrator<Void, Unit, Unit> integrator = Gatherer.Integrator.of((_, element, downstream) ->
                downstream.push(element));
        return Gatherer.ofSequential(integrator);
    }
}
