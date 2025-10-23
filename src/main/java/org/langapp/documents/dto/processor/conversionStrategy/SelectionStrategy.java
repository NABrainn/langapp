package org.langapp.documents.dto.processor.conversionStrategy;

public sealed interface SelectionStrategy permits NoSelection, WordSelection, PhraseSelection {
}
