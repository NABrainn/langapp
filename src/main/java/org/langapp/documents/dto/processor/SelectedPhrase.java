package org.langapp.documents.dto.processor;

import java.util.Objects;

public record SelectedPhrase(int id,
                             String rawContent) implements Phrase {
    public SelectedPhrase {
        Objects.requireNonNull(rawContent);
    }
}
