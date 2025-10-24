package org.langapp.documents.dto.processor;

import java.util.Objects;

public record InvalidWord(int id,
                          String rawContent) implements Word {
    public InvalidWord {
        Objects.requireNonNull(rawContent);
    }
}
