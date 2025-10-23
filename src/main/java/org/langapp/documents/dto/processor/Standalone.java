package org.langapp.documents.dto.processor;

import java.util.Objects;

public record Standalone(int id,
                         String rawContent) implements Word {
    public Standalone {
        Objects.requireNonNull(rawContent);
    }
}
