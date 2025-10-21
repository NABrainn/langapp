package org.langapp.documents.component;

import org.langapp.documents.dto.processor.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DocumentProcessor {

    public List<Unit> convertToUnits(String content) {
        Objects.requireNonNull(content);
        var splitContent = content.split(" ");
        var units = Arrays.stream(splitContent)
                .flatMap(textUnit -> textUnit.contains("\n") ?
                        Arrays.stream((textUnit.substring(0, textUnit.lastIndexOf("\n")) + " " + textUnit.substring(textUnit.lastIndexOf("\n"))).split(" ")) :
                        Stream.of(textUnit));
        units.forEach(System.out::println);
        return null;
    }
}
