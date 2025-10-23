package org.langapp.translations.dto;

import java.util.Objects;

public record FromLanguageDetails(Language language,
                                  String content) {
    public FromLanguageDetails {
        Objects.requireNonNull(language);
        Objects.requireNonNull(content);
    }
}
