package org.langapp.documents.dto.processor.word;

public sealed interface NewWord
        extends Word
        permits SelectedNewWord, UnselectedNewWord {
}
