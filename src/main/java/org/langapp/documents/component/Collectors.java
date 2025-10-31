package org.langapp.documents.component;

import org.langapp.documents.dto.processor.Paragraph;
import org.langapp.documents.dto.processor.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors {
    public record ParagraphState(ArrayList<Unit> buffer,
                                 ArrayList<Paragraph> paragraphs,
                                 AtomicInteger atomicInteger) {
    }
    public Collector<Unit, ParagraphState, List<Paragraph>> toParagraphs() {
        Supplier<ParagraphState> supplier = () -> new ParagraphState(new ArrayList<>(), new ArrayList<>(), new AtomicInteger(0));
        BiConsumer<ParagraphState, Unit> accumulator = (state, unit) -> {
            state.buffer().add(unit);
            var last = state.buffer().getLast();
            int paragraphSize = last.rawContent().length() - last.rawContent().replace("\n", "").length();
            if(paragraphSize > 0) {
                var paragraph = new Paragraph(List.copyOf(state.buffer()), paragraphSize, state.atomicInteger().getAndIncrement());
                state.paragraphs().add(paragraph);
                state.buffer().clear();
            }
        };
        BinaryOperator<ParagraphState> combiner = (left, right) -> {
            left.buffer().addAll(right.buffer());
            return left;
        };
        Function<ParagraphState, List<Paragraph>> finisher = ParagraphState::paragraphs;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }
}
