package org.langapp.documents.dto.processor;

public sealed interface Word
        extends Unit
        permits InvalidWord, NewWord, PhrasePart, SelectedWord, Standalone, TranslatedWord {

}
