package org.langapp.documents.dto.processor.phrase;

public sealed interface NewPhrase
        extends Phrase
        permits SelectedNewPhrase, UnselectedNewPhrase{
}
