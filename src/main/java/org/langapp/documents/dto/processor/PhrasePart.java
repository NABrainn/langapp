package org.langapp.documents.dto.processor;

import java.util.Objects;

public record PhrasePart(int id,
                         String rawContent,
                         String content) implements Word {
    public PhrasePart {
        Objects.requireNonNull(rawContent);
    }
}
