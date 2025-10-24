package org.langapp.documents.dto.processor;

import org.langapp.translations.dto.Translation;

import java.util.Objects;

public record TranslatedPhrase(int id,
                               int size,
                               String rawContent,
                               Translation translation) implements Phrase {
    public TranslatedPhrase {
        Objects.requireNonNull(rawContent);
    }

    @Override
    public Unit withId(int id) {
        return new TranslatedPhrase(id, size, rawContent, translation);
    }
}
