package org.langapp.documents.dto.processor;

import java.util.Objects;

public record PhrasePart(int id,
                         String rawContent) implements Word {
    public PhrasePart {
        Objects.requireNonNull(rawContent);
    }

    @Override
    public Word withId(int id) {
        return new PhrasePart(id, rawContent);
    }
}
