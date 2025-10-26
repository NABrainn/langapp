package org.langapp.documents.component;

import org.langapp.documents.dto.ProcessedDocument;
import org.langapp.documents.dto.processor.ConversionDetails;
import org.langapp.documents.dto.processor.selection.SelectionStrategy;
import org.langapp.translations.dto.*;

import java.util.List;
import java.util.Map;

public class DocumentService {
    public static ProcessedDocument findById(int documentId, SelectionStrategy selection) {

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
        var v44 = new Translation(
                new FromLanguageDetails(Language.NO, "er"),
                new ToLanguageDetails(Language.EN, "is"),
                Level.FAMILIAR
        );
        var mockTranslatedWords = Map.of(
                v1.fromLangDetails().content(), v1,
                v2.fromLangDetails().content(), v2,
                v3.fromLangDetails().content(), v3,
                v44.fromLangDetails().content(), v44
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

        var conversion = new ConversionDetails(mockContent, selection, mockTranslatedPhrases, mockTranslatedWords);
        var processor = new Processor();
        var units = processor.convertToUnits(conversion);
        return new ProcessedDocument(units, selection);
    }
}
