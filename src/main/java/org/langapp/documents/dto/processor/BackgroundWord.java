package org.langapp.documents.dto.processor;

import org.langapp.translations.dto.Translation;

import java.util.Objects;

public record BackgroundWord(int id,
                             String rawContent) implements Word {
    public BackgroundWord {
        Objects.requireNonNull(rawContent);
    }
}