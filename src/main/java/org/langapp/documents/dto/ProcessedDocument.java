package org.langapp.documents.dto;

import org.langapp.documents.dto.processor.Paragraph;
import org.langapp.documents.dto.processor.selection.SelectionStrategy;

import java.util.List;

public record ProcessedDocument(List<Paragraph> units,
                                SelectionStrategy selectionStrategy) {
}
