package org.langapp.documents.dto.processor.conversionStrategy;

import java.util.Objects;

public record PhraseSelection(int startId,
                              int endId,
                              String rawContent) implements SelectionStrategy {
    public PhraseSelection {
        Objects.requireNonNull(rawContent);
    }
}
