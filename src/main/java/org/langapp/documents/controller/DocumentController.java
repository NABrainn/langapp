package org.langapp.documents.controller;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.langapp.documents.component.DocumentProcessor;
import org.langapp.documents.dto.processor.Unit;

import java.util.List;
import java.util.Map;

public class DocumentController {
    public static void create(@NotNull Context context) {
        context.render("base.jte", Map.of("base", "base"));
    }

    public static void findById(@NotNull Context context) {
        String title = "Budsjettfloke utløser kravkrig: – Skal rydde opp raskt";
        String content = """
        – Så det du sier er at hvis Arbeiderpartiet nå gir etter for Senterpartiets krav, endrer litt på budsjettet før de setter seg til forhandlingsbordet, så vil dere også kreve det samme?
        – Ja, helt opplagt. Vi har kjempa for hver krone i økning av barnetrygd. Arbeiderpartiet har snakka som om de har gjort det sjøl, og nå kutter de i barnetrygda til de som er aller fattigst.
        – Nå stiller du faktisk et ultimatum og sier at hvis Støre gir etter for Senterparti -krav, før forhandlingene starter, så vil dere også kreve det samme.
        – Dette er bare en naturlig konsekvens, for det vil være helt urimelig om de som roper høyest og stiller seg på sin linje, skal bli hørt. Mens vi ikke skal bli det. Og skal Senterpartiets kutt rettes opp, ja, så tar jeg for gitt at det også gjelder SVs kutt.
        """;
        var processor = new DocumentProcessor();
        List<Unit> units = processor.convertToUnits(content);
        context.render("pages/document/document-page.jte", Map.of("title", title, "content", content));
    }
}
