package org.langapp.documents.dto.processor;

import java.util.Objects;

public record NewPhrase(int id,
                        int size,
                        String rawContent,
                        String content) implements Phrase {
    public NewPhrase {
        Objects.requireNonNull(rawContent);
    }
}