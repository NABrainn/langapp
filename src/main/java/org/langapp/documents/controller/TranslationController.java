package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.documents.dto.processor.selection.WordSelection;

import java.util.Map;
import java.util.Objects;

public class TranslationController {
    public static void wordTranslationForm(@NotNull Context context) {
        var id = Integer.parseInt(Objects.requireNonNull(context.queryParam("id")));
        var fromWord = Objects.requireNonNull(context.queryParam("fromWord"));

        var path = "pages/translations/word-word-translation-form.jte";
        var attributes = Map.of("selection", new WordSelection(id, fromWord));
        context.render(path, attributes);
    }
    public static void phraseTranslationForm(@NotNull Context context) {
        var fromWord = Objects.requireNonNull(context.queryParam("fromPhrase"));
        var startId = Integer.parseInt(Objects.requireNonNull(context.queryParam("startId")));
        var endId = Integer.parseInt(Objects.requireNonNull(context.queryParam("endId")));

        var path = "pages/translations/phrase-word-translation-form.jte";
        var attributes = Map.of("selection", new PhraseSelection(startId, endId, fromWord, fromWord));
        context.render(path, attributes);
    }
    public static void translationList(@NotNull Context context) {
        var path = "pages/translations/translation-list.jte";
        context.render(path);
    }

    public static void updateLevel(@NotNull Context context) {
        var selection = Objects.requireNonNull(context.queryParam("selection"));
        var documentId = Integer.parseInt(Objects.requireNonNull(context.queryParam("documentId")));
        var contentParam = Objects.requireNonNull(context.queryParam("content"));
        var nextLevel = Integer.parseInt(Objects.requireNonNull(context.queryParam("nextLevel")));

        var selectionStrategy = switch (selection) {
            case "word" -> {
                var wordId = Integer.parseInt(Objects.requireNonNull(context.queryParam("wordId")));
                yield new WordSelection(wordId, contentParam);
            }
            case "phrase" ->  {
                var startId = Integer.parseInt(Objects.requireNonNull(context.queryParam("startId")));
                var endId = Integer.parseInt(Objects.requireNonNull(context.queryParam("endId")));
                var rawContent = Objects.requireNonNull(context.queryParam("rawContent"));
                yield new PhraseSelection(startId, endId, contentParam, rawContent);
            }
            default -> throw new IllegalStateException("Unexpected value: " + selection);
        };

        var path = "pages/translations/levels.jte";
        var attributes = Map.of(
                "documentId", documentId,
                "selection", selectionStrategy,
                "reloading", true,
                "currentLevel", nextLevel
        );
        context.render(path, attributes);
    }
}
