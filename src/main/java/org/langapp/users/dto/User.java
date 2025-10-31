package org.langapp.users.dto;

import org.langapp.translations.dto.Language;

import java.util.Objects;

public record User(String username,
                   String password,
                   Role role,
                   Language fromLanguage,
                   Language toLanguage,
                   Language uiLanguage) {
    public User {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(role);
        Objects.requireNonNull(fromLanguage);
        Objects.requireNonNull(toLanguage);
        Objects.requireNonNull(uiLanguage);
    }
}
