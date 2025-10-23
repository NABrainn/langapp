package org.langapp.documents.dto.processor;

public sealed interface Word
        extends Unit
        permits
        NewWord, TranslatedWord,
        PhrasePart, Standalone,
        SelectedWord, BackgroundWord{
}
