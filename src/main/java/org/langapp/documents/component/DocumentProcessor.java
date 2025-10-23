package org.langapp.documents.component;

import org.langapp.documents.dto.processor.*;
import org.langapp.documents.dto.processor.conversionStrategy.NoSelection;
import org.langapp.documents.dto.processor.conversionStrategy.PhraseSelection;
import org.langapp.documents.dto.processor.conversionStrategy.WordSelection;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DocumentProcessor {

    public List<Unit> convertToUnits(ConversionDetails details) {
        Objects.requireNonNull(details);
        var spaceSeparatedContent = details.content().split(" ");
        var gatherers = new ProcessorGatherers();
        return Arrays.stream(spaceSeparatedContent)
                .flatMap(wordUnit -> wordUnit.contains("\n") ?
                        Arrays.stream((wordUnit.substring(0, wordUnit.lastIndexOf("\n")) + " " + wordUnit.substring(wordUnit.lastIndexOf("\n"))).split(" ")) :
                        Stream.of(wordUnit))
                .gather(gatherers.mapWords(details.translatedPhrases()))
                .gather(gatherers.allocatePhrases(details.translatedPhrases()))
                .gather(gatherers.mapWordTranslations(details.translatedWords()))
                .gather(switch (details.selectionStrategy()){
                    case NoSelection _ -> gatherers.doNothing();
                    case PhraseSelection phraseSelection -> gatherers.allocateSelectedPhrase(phraseSelection);
                    case WordSelection wordSelection -> gatherers.allocateSelectedWord(wordSelection);
                })
                .toList();
    }
}
