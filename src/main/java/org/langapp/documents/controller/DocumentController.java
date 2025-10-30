package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.component.DocumentService;
import org.langapp.documents.dto.processor.selection.NoSelection;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.documents.dto.processor.selection.WordSelection;
import org.langapp.translations.dto.*;

import java.util.Map;
import java.util.Objects;

public class DocumentController {

    public static void create(@NotNull Context context) {
        context.render("base.jte", Map.of("base", "base"));
    }

    public static void findById(@NotNull Context context) {
        var selection = switch (context.queryParam("selection")){
            case "phrase" -> {
                var startIdParam = Objects.requireNonNull(context.queryParam("startId"));
                var endIdParam = Objects.requireNonNull(context.queryParam("endId"));
                var contentParam = Objects.requireNonNull(context.queryParam("content"));
                var rawContentParam = Objects.requireNonNull(context.queryParam("rawContent"));

                var startId = Integer.parseInt(startIdParam);
                var endId = Integer.parseInt(endIdParam);
                yield new PhraseSelection(startId, endId, contentParam, rawContentParam);
            }
            case "word" -> {
                var wordIdParam = Objects.requireNonNull(context.queryParam("wordId"));
                var wordContentParam = Objects.requireNonNull(context.queryParam("content"));

                var wordId = Integer.parseInt(wordIdParam);
                yield new WordSelection(wordId, wordContentParam);
            }
            case "none" -> new NoSelection();
            case null -> new NoSelection();
            default -> throw new IllegalStateException("Unexpected value: " + context.queryParam("selection"));
        };
        var id = Integer.parseInt(context.pathParam("id"));
        var document = DocumentService.findById(id, selection);
        var mockTitle = "Budsjettfloke utløser kravkrig: – Skal rydde opp raskt";
        switch (document.selectionStrategy()) {
            case NoSelection noSelection -> {
                var path = "pages/document/document-page.jte";
                var attributes = Map.of(
                        "documentId", 1,
                        "title", mockTitle,
                        "paragraphs", document.units(),
                        "selection", noSelection
                );
                context.render(path, attributes);
            }
            case PhraseSelection phraseSelection -> {
                var path = "pages/document/document-page-content.jte";
                var attributes = Map.of(
                        "documentId", 1,
                        "title", mockTitle,
                        "paragraphs", document.units(),
                        "selection", phraseSelection
                );
                context.render(path, attributes);
            }
            case WordSelection wordSelection -> {
                var path = "pages/document/document-page-content.jte";
                var attributes = Map.of(
                        "documentId", 1,
                        "title", mockTitle,
                        "paragraphs", document.units(),
                        "selection", wordSelection
                );
                context.render(path, attributes);
            }
        };
    }
}
