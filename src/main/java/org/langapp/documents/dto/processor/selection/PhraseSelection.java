package org.langapp.documents.dto.processor.selection;

import java.util.Objects;

public record PhraseSelection(int startId,
                              int endId,
                              String content,
                              String rawContent) implements SelectionStrategy {
    public PhraseSelection {
        Objects.requireNonNull(rawContent);
    }
}
