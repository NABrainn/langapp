package org.langapp.documents.dto.processor;

import org.langapp.documents.dto.processor.conversionStrategy.SelectionStrategy;
import org.langapp.translations.dto.Translation;

import java.util.Map;
import java.util.Objects;

public record ConversionDetails(String content,
                                SelectionStrategy selectionStrategy,
                                Map<String, Translation> translatedPhrases,
                                Map<String, Translation> translatedWords) {
    public ConversionDetails {
        Objects.requireNonNull(content);
        Objects.requireNonNull(selectionStrategy);
        Objects.requireNonNull(translatedWords);
        Objects.requireNonNull(translatedPhrases);
        translatedWords.forEach((k, v) -> {
            Objects.requireNonNull(k);
            Objects.requireNonNull(v);
        });
        translatedPhrases.forEach((k, v) -> {
            Objects.requireNonNull(k);
            Objects.requireNonNull(v);
        });
    }

    @Override
    public Map<String, Translation> translatedPhrases() {
        return Map.copyOf(translatedPhrases);
    }

    @Override
    public Map<String, Translation> translatedWords() {
        return Map.copyOf(translatedWords);
    }
}
