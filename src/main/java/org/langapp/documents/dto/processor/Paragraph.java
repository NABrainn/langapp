package org.langapp.documents.dto.processor;

import java.util.List;
import java.util.Objects;

public record Paragraph(List<Unit> units,
                        int size,
                        int id) {
    public Paragraph {
        Objects.requireNonNull(units);
    }

    public List<Unit> units() {
        return List.copyOf(units);
    }
}
