package org.langapp.documents.dto.processor;

import java.util.Objects;

public record InvalidWord(int id,
                          String rawContent,
                          String content) implements Word {
    public InvalidWord {
        Objects.requireNonNull(rawContent);
    }
}
