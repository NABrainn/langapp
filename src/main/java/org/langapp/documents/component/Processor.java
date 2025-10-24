package org.langapp.documents.component;

import org.langapp.documents.dto.processor.*;
import org.langapp.documents.dto.processor.selection.NoSelection;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.documents.dto.processor.selection.WordSelection;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Processor {

    public List<Paragraph> convertToUnits(ConversionDetails details) {
        Objects.requireNonNull(details);
        var spaceSeparatedContent = details.content().split(" ");
        var gatherers = new Gatherers();
        var collectors = new Collectors();
        return Arrays.stream(spaceSeparatedContent)
                .flatMap(wordText -> wordText.contains("\n") ?
                        Arrays.stream((wordText.substring(0, wordText.lastIndexOf("\n") + 1) + " " + wordText.substring(wordText.lastIndexOf("\n") + 1)).split(" ")) :
                        Stream.of(wordText))
                .gather(gatherers.mapWords(details.translatedPhrases()))
                .gather(gatherers.allocatePhrases(details.translatedPhrases()))
                .gather(gatherers.mapWordTranslations(details.translatedWords()))
                .gather(gatherers.cleanupIds())
                .gather(switch (details.selectionStrategy()){
                    case NoSelection _ -> gatherers.doNothing();
                    case PhraseSelection phraseSelection -> gatherers.allocateSelectedPhrase(phraseSelection);
                    case WordSelection wordSelection -> gatherers.allocateSelectedWord(wordSelection);
                })
//                .gather(gatherers.cleanupIds())
                .collect(collectors.toParagraphs());
    }
}
