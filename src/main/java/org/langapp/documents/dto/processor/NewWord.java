package org.langapp.documents.dto.processor;

import java.util.Objects;

public record NewWord(int id,
                      String rawContent) implements Word {
    public NewWord {
        Objects.requireNonNull(rawContent);
    }

    @Override
    public Word withId(int id) {
        return new NewWord(id, rawContent);
    }
}