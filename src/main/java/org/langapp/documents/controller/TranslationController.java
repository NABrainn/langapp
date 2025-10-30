package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.documents.dto.processor.selection.WordSelection;

import java.util.Map;
import java.util.Objects;

public class TranslationController {
    public static void wordTranslationForm(@NotNull Context context) {
        var idParam = Objects.requireNonNull(context.queryParam("id"));
        var fromWordParam = Objects.requireNonNull(context.queryParam("fromWord"));
        var id = Integer.parseInt(idParam);
        var path = "pages/translations/word-word-translation-form.jte";
        var attributes = Map.of("selection", new WordSelection(id, fromWordParam));
        context.render(path, attributes);
    }
    public static void phraseTranslationForm(@NotNull Context context) {
        var startIdParam = Objects.requireNonNull(context.queryParam("startId"));
        var endIdParam = Objects.requireNonNull(context.queryParam("endId"));
        var fromWordParam = Objects.requireNonNull(context.queryParam("fromPhrase"));
        var startId = Integer.parseInt(startIdParam);
        var endId = Integer.parseInt(endIdParam);
        var path = "pages/translations/phrase-word-translation-form.jte";
        var attributes = Map.of("selection", new PhraseSelection(startId, endId, fromWordParam, fromWordParam));
        context.render(path, attributes);
    }
    public static void translationList(@NotNull Context context) {
        var path = "pages/translations/translation-list.jte";
        context.render(path);
    }

    public static void updateLevel(@NotNull Context context) {
        String selectionParam = Objects.requireNonNull(context.queryParam("selection"));
        String documentIdParam = Objects.requireNonNull(context.queryParam("documentId"));
        String contentParam = Objects.requireNonNull(context.queryParam("content"));
        String nextLevelParam = Objects.requireNonNull(context.queryParam("nextLevel"));
        var selection = switch (selectionParam) {
            case "word" -> {
                var wordId = Integer.parseInt(Objects.requireNonNull(context.queryParam("wordId")));
                yield new WordSelection(wordId, contentParam);
            }
            case "phrase" ->  {
                var startId = Integer.parseInt(Objects.requireNonNull(context.queryParam("startId")));
                var endId = Integer.parseInt(Objects.requireNonNull(context.queryParam("endId")));
                var rawContent = Objects.requireNonNull("rawContent");
                yield new PhraseSelection(startId, endId, contentParam, rawContent);
            }
            default -> throw new IllegalStateException("Unexpected value: " + selectionParam);
        };


        var documentId = Integer.parseInt(documentIdParam);
        var nextLevel = Integer.parseInt(nextLevelParam);
        var path = "pages/translations/levels.jte";
        var attributes = Map.of(
                "documentId", documentId,
                "selection", selection,
                "reloading", true,
                "currentLevel", nextLevel
        );
        context.render(path, attributes);
    }
}
