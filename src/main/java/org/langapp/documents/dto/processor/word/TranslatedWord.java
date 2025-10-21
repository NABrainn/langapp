package org.langapp.documents.dto.processor.word;

public sealed interface TranslatedWord
        extends Word
        permits SelectedTranslatedWord, UnselectedTranslatedWord{
}
