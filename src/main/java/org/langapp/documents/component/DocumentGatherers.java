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

final public class DocumentGatherers {

    private final StringUtil stringUtil;

    public DocumentGatherers(StringUtil stringUtil) {
        this.stringUtil = stringUtil;
    }

    public interface WordMapperState {
        Predicate<String> isPhrasePart();
        AtomicInteger idStore();
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
        };

        Gatherer.Integrator<WordMapperState, String, Word> integrator = Gatherer.Integrator.of((state, wordStr, downstream) -> {
            var unit = (Word)(state.isPhrasePart().test(stringUtil.normalized(wordStr)) ?
                    new PhrasePart(state.idStore().getAndIncrement(), wordStr) :
                    new Standalone(state.idStore().getAndIncrement(), wordStr));
            return downstream.push(unit);
        });
        return Gatherer.ofSequential(accumulator, integrator);
    }

    public interface PhraseAllocationState {
        Function<String, Optional<Translation>> phraseExtractor(Map<String, Translation> translatedPhrases);
        ArrayList<Word> buffer();
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
        };
        Gatherer.Integrator<PhraseAllocationState, Word, Unit> integrator = Gatherer.Integrator.of((state, unit, downstream) -> {
            if(unit instanceof PhrasePart phrasePartWord) {
                state.buffer().add(phrasePartWord);
                var bufferValue = String.join(" ", state.buffer().stream()
                        .map(_unit -> stringUtil.normalized(_unit.rawContent()))
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

    public Gatherer<Unit, Function<String, Optional<Translation>>, Unit> mapWordTranslations(Map<String, Translation> translatedWords) {
        Supplier<Function<String, Optional<Translation>>> state = () -> (word) -> Optional.ofNullable(translatedWords.get(word));
        Gatherer.Integrator<Function<String, Optional<Translation>>, Unit, Unit> integrator = Gatherer.Integrator.of((_, unit, downstream) -> {
            var element = switch (unit) {
                case Phrase phrase -> phrase;
                case Word word -> state.get().apply(stringUtil.normalized(word.rawContent()))
                        .map(translation -> (Unit) new TranslatedWord(word.id(), word.rawContent(), translation))
                        .orElse(new NewWord(word.id(), word.rawContent()));
            };
            return downstream.push(element);
        });
        return Gatherer.ofSequential(state, integrator);
    }

    public Gatherer<Unit, ArrayList<Unit>, Unit> allocateSelectedWord(WordSelection wordSelection) {
        Objects.requireNonNull(wordSelection);
        return null;
    }

    public Gatherer<Unit, ArrayList<Unit>, Unit> allocateSelectedPhrase(PhraseSelection phraseSelection) {
        Objects.requireNonNull(phraseSelection);
        return null;
    }

    public Gatherer<Unit, ?, Unit> doNothing() {
        Gatherer.Integrator<Void, Unit, Unit> integrator = Gatherer.Integrator.of((_, element, downstream) -> downstream.push(element));
        return Gatherer.ofSequential(integrator);
    }
}
