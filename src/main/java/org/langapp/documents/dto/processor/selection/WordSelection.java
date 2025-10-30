package org.langapp.documents.dto.processor.selection;

import java.util.Objects;

public record WordSelection(int wordId,
                            String content) implements SelectionStrategy {
    public WordSelection {
        Objects.requireNonNull(content);
    }
}
