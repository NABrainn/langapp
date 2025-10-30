package org.langapp.documents.dto.processor;

import java.util.Objects;

public record NewWord(int id,
                      String rawContent,
                      String content) implements Word {
    public NewWord {
        Objects.requireNonNull(rawContent);
    }
}