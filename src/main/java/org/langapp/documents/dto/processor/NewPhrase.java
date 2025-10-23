package org.langapp.documents.dto.processor;

import java.util.Objects;

public record NewPhrase(int id,
                        int size,
                        String rawContent) implements Phrase {
    public NewPhrase {
        Objects.requireNonNull(rawContent);
    }
}