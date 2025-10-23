package org.langapp.translations.dto;

import java.util.Objects;

public record Translation(FromLanguageDetails fromLangDetails,
                          ToLanguageDetails toLangDetails,
                          Level level) {
    public Translation {
        Objects.requireNonNull(fromLangDetails);
        Objects.requireNonNull(toLangDetails);
        Objects.requireNonNull(level);
    }
}
