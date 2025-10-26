package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.component.DocumentService;
import org.langapp.documents.dto.context.DocumentPageContext;
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
                var startIdParam = context.queryParam("startId");
                var endIdParam = context.queryParam("endId");
                var rawContentParam = context.queryParam("rawContent");
                Objects.requireNonNull(startIdParam);
                Objects.requireNonNull(endIdParam);
                Objects.requireNonNull(rawContentParam);
                var startId = Integer.parseInt(startIdParam);
                var endId = Integer.parseInt(endIdParam);
                yield new PhraseSelection(startId, endId, rawContentParam);
            }
            case "word" -> {
                var wordIdParam = context.queryParam("wordId");
                Objects.requireNonNull(wordIdParam);
                var wordId = Integer.parseInt(wordIdParam);
                yield new WordSelection(wordId);
            }
            case "none" -> new NoSelection();
            case null -> new NoSelection();
            default -> throw new IllegalStateException("Unexpected value: " + context.queryParam("selection"));
        };
        var id = Integer.parseInt(context.pathParam("id"));
        var document = DocumentService.findById(id, selection);
        var mockTitle = "Budsjettfloke utløser kravkrig: – Skal rydde opp raskt";
        var path = switch (document.selectionStrategy()) {
            case NoSelection _ -> "pages/document/document-page.jte";
            case PhraseSelection _ -> "pages/document/document-page-content.jte";
            case WordSelection _ -> "pages/document/document-page-content.jte";
        };
        var attributes = Map.of("title", mockTitle, "paragraphs", document.units());
        context.render(path, attributes);
    }
}
