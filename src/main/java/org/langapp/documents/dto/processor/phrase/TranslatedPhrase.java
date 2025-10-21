package org.langapp.documents.dto.processor.phrase;

public sealed interface TranslatedPhrase
        extends Phrase
        permits SelectedTranslatedPhrase, UnselectedTranslatedPhrase{
}
