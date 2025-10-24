package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.component.Processor;
import org.langapp.documents.dto.processor.ConversionDetails;
import org.langapp.documents.dto.processor.selection.PhraseSelection;
import org.langapp.translations.dto.*;

import java.util.Map;

public class DocumentController {
    public static void create(@NotNull Context context) {
        context.render("base.jte", Map.of("base", "base"));
    }

    public static void findById(@NotNull Context context) {
        var mockTitle = "Budsjettfloke utløser kravkrig: – Skal rydde opp raskt";
        var mockContent = """
        - Så det du sier er at hvis Arbeiderpartiet nå gir etter for Senterpartiets krav, endrer litt på budsjettet før de setter seg til forhandlingsbordet, så vil dere også kreve det samme?
        - Ja, helt opplagt. Vi har kjempa for hver krone i økning av barnetrygd. Arbeiderpartiet har snakka som om de har gjort det sjøl, og nå kutter de i barnetrygda til de som er aller fattigst.
        - Nå stiller du faktisk et ultimatum og sier at hvis Støre gir etter for Senterparti -krav, før forhandlingene starter, så vil dere også kreve det samme.
        
        
        
        
        
        - Dette er bare en naturlig konsekvens, for det vil være helt urimelig om de som roper høyest og stiller seg på sin linje, skal bli hørt. Mens vi ikke skal bli det. Og skal Senterpartiets kutt rettes opp, ja, så tar jeg for gitt at det også gjelder SVs kutt.
        """;
        var v1 = new Translation(
                new FromLanguageDetails(Language.NO, "så"),
                new ToLanguageDetails(Language.EN, "so"),
                Level.NEW
        );
        var v2 = new Translation(
                new FromLanguageDetails(Language.NO, "vil"),
                new ToLanguageDetails(Language.EN, "will"),
                Level.RECOGNIZED
        );
        var v3 = new Translation(
                new FromLanguageDetails(Language.NO, "stiller"),
                new ToLanguageDetails(Language.EN, "await"),
                Level.FAMILIAR
        );
        var mockTranslatedWords = Map.of(
                v1.fromLangDetails().content(), v1,
                v2.fromLangDetails().content(), v2,
                v3.fromLangDetails().content(), v3
        );
        var v4 = new Translation(
                new FromLanguageDetails(Language.NO, "så vil"),
                new ToLanguageDetails(Language.EN, "so will"),
                Level.RECOGNIZED
        );
        var v5 = new Translation(
                new FromLanguageDetails(Language.NO, "for det vil være"),
                new ToLanguageDetails(Language.EN, "so that will be"),
                Level.RECOGNIZED
        );
        var v6 = new Translation(
                new FromLanguageDetails(Language.NO, "så tar"),
                new ToLanguageDetails(Language.EN, "so takes"),
                Level.RECOGNIZED
        );
        var mockTranslatedPhrases = Map.of(
                "så vil", v4,
                "for det vil være", v5,
                "så tar", v6
        );
        var conversion = new ConversionDetails(mockContent, new PhraseSelection(98, 99, "om de"), mockTranslatedPhrases, mockTranslatedWords);
        var processor = new Processor();
        var units = processor.convertToUnits(conversion);
        var path = "pages/document/document-page.jte";
        var params = Map.of("title", mockTitle, "paragraphs", units);
        context.render(path, params);
    }
}
