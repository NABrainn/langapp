package org.langapp.documents.dto.processor;

import org.langapp.translations.dto.Translation;

import java.util.Objects;

public record TranslatedWord(int id,
                             String rawContent,
                             Translation translation) implements Word {
    public TranslatedWord {
        Objects.requireNonNull(rawContent);
    }
}