package org.langapp.documents.dto.processor;

public sealed interface Phrase
        extends Unit
        permits NewPhrase, SelectedPhrase, TranslatedPhrase {
}
