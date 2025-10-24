package org.langapp.documents.dto.processor;

import java.util.Objects;

public record SelectedWord(int id,
                           String rawContent) implements Word {
    public SelectedWord {
        Objects.requireNonNull(rawContent);
    }

    @Override
    public Word withId(int id) {
        return new SelectedWord(id, rawContent);
    }
}