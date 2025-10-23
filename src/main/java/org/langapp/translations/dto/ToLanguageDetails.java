package org.langapp.translations.dto;

import java.util.Objects;

public record ToLanguageDetails(Language language,
                                String content) {
    public ToLanguageDetails {
        Objects.requireNonNull(language);
        Objects.requireNonNull(content);
    }
}
