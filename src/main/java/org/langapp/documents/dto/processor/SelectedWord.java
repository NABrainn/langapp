package org.langapp.documents.dto.processor;

import java.util.Objects;

public record SelectedWord(int id,
                           String rawContent,
                           String content) implements Word {
    public SelectedWord {
        Objects.requireNonNull(rawContent);
    }
}