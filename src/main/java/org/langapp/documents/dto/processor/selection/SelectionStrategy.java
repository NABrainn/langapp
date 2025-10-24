package org.langapp.documents.dto.processor.selection;

public sealed interface SelectionStrategy permits NoSelection, WordSelection, PhraseSelection {
}
