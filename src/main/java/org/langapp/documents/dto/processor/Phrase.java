package org.langapp.documents.dto.processor;

import org.langapp.translations.dto.Translation;

import java.util.Objects;

public record Phrase(int id,
                     int size,
                     String rawContent,
                     Translation translation) implements Unit {
    public Phrase {
        Objects.requireNonNull(rawContent);
    }
}
