package org.langapp.documents.component;

import org.langapp.documents.dto.processor.*;
import org.langapp.documents.dto.processor.conversionStrategy.PhraseSelection;
import org.langapp.documents.dto.processor.conversionStrategy.WordSelection;
import org.langapp.string.StringUtil;
import org.langapp.translations.dto.Translation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

final public class ProcessorGatherers {

    public interface WordMapperState {
        Predicate<String> isPhrasePart();
        AtomicInteger idStore();
        StringUtil stringUtil();
    }

    public Gatherer<String, WordMapperState, Word> mapWords(Map<String, Translation> translatedPhrases) {
        Supplier<WordMapperState> accumulator = () -> new WordMapperState() {
            @Override
            public Predicate<String> isPhrasePart() {
                return (word) -> translatedPhrases.values().stream()
                        .anyMatch(phrase -> phrase
                                .fromLangDetails().content()
                                .contains(word));
            }

            @Override
            public AtomicInteger idStore() {
                return new AtomicInteger(0);
            }

            @Override
            public StringUtil stringUtil() {
                return new StringUtil();
            }
        };

        Gatherer.Integrator<WordMapperState, String, Word> integrator = Gatherer.Integrator.of((state, wordStr, downstream) -> {
            var unit = (Word)(state.isPhrasePart().test(state.stringUtil().normalized(wordStr)) ?
                    new PhrasePart(state.idStore().getAndIncrement(), wordStr) :
                    new Standalone(state.idStore().getAndIncrement(), wordStr));
            return downstream.push(unit);
        });
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public interface PhraseAllocationState {
        Function<String, Optional<Translation>> phraseExtractor(Map<String, Translation> translatedPhrases);
        ArrayList<Word> buffer();
        StringUtil stringUtil();
    }

    public Gatherer<Word, PhraseAllocationState, Unit> allocatePhrases(Map<String, Translation> translatedPhrases) {
        Supplier<PhraseAllocationState> accumulator = () -> new PhraseAllocationState() {
            @Override
            public Function<String, Optional<Translation>> phraseExtractor(Map<String, Translation> translatedPhrases) {
                return (value) -> translatedPhrases.values().stream()
                        .filter(translation -> translation.fromLangDetails().content()
                                .trim()
                                .equalsIgnoreCase(value))
                        .findFirst();
            }

            @Override
            public ArrayList<Word> buffer() {
                return new ArrayList<>();
            }

            @Override
            public StringUtil stringUtil() {
                return new StringUtil();
            }
        };
        Gatherer.Integrator<PhraseAllocationState, Word, Unit> integrator = Gatherer.Integrator.of((state, unit, downstream) -> {
            if(unit instanceof PhrasePart phrasePartWord) {
                state.buffer().add(phrasePartWord);
                var bufferValue = String.join(" ", state.buffer().stream()
                        .map(_unit -> state.stringUtil().normalized(_unit.rawContent()))
                        .toList());
                var optPhrase = state.phraseExtractor(translatedPhrases).apply(bufferValue);
                if(optPhrase.isPresent()) {
                    var phraseTranslation = optPhrase.get();

                    var id = state.buffer().getFirst().id();
                    var rawContent = String.join(" ", state.buffer().stream()
                            .map(Unit::rawContent)
                            .toList());
                    var size = state.buffer().size();
                    var phrase = new Phrase(id, size, rawContent, phraseTranslation);
                    downstream.push(phrase);
                    state.buffer().clear();
                }
            }
            else {
                state.buffer().forEach(downstream::push);
                downstream.push(unit);
                state.buffer().clear();
            }
            return downstream.push(unit);
        });
        BiConsumer<PhraseAllocationState, Gatherer.Downstream<? super Unit>> finisher = (state, downstream) ->
                state.buffer().forEach(downstream::push);
        return Gatherer.ofSequential(accumulator, integrator, finisher);
    }

    public interface WordTranslationsState {
        Function<String, Optional<Translation>> translationExtractor();
        StringUtil stringUtil();
    }
    public Gatherer<Unit, WordTranslationsState, Unit> mapWordTranslations(Map<String, Translation> translatedWords) {
        Supplier<WordTranslationsState> accumulator = () -> new WordTranslationsState() {
            @Override
            public Function<String, Optional<Translation>> translationExtractor() {
                return (word) -> Optional.ofNullable(translatedWords.get(word));
            }

            @Override
            public StringUtil stringUtil() {
                return new StringUtil();
            }
        };
        Gatherer.Integrator<WordTranslationsState, Unit, Unit> integrator = Gatherer.Integrator.of((state, unit, downstream) ->
                downstream.push(switch (unit) {
                    case Phrase phrase -> phrase;
                    case Word word -> state.translationExtractor().apply(state.stringUtil().normalized(word.rawContent()))
                            .map(translation -> (Unit) new TranslatedWord(word.id(), word.rawContent(), translation))
                            .orElse(new NewWord(word.id(), word.rawContent()));
                }));
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public Gatherer<Unit, ?, Unit> allocateSelectedWord(WordSelection details) {
        Objects.requireNonNull(details);
        Gatherer.Integrator<Void, Unit, Unit> integrator = ((_, unit, downstream) ->
                unit.id() == details.wordId() ?
                        downstream.push(new SelectedWord(unit.id(), unit.rawContent())) :
                        downstream.push(unit)
        );
        return Gatherer.ofSequential(integrator);
    }
    public interface SelectedPhraseAllocationState {
        ArrayList<Word> buffer();
    }

    public Gatherer<Unit, SelectedPhraseAllocationState, Unit> allocateSelectedPhrase(PhraseSelection details) {
        Objects.requireNonNull(details);
        Supplier<SelectedPhraseAllocationState> accumulator = () -> new SelectedPhraseAllocationState() {
            @Override
            public ArrayList<Word> buffer() {
                return null;
            }
        };
        Gatherer.Integrator<SelectedPhraseAllocationState, Unit, Unit> integrator = Gatherer.Integrator.of((state, element, downstream) -> {
            return downstream.push(element);
        });
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public Gatherer<Unit, ?, Unit> doNothing() {
        Gatherer.Integrator<Void, Unit, Unit> integrator = Gatherer.Integrator.of((_, element, downstream) -> downstream.push(element));
        return Gatherer.ofSequential(integrator);
    }
}
