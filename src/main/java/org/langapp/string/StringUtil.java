package org.langapp.string;

public interface StringUtil {
    default String normalizeString(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}\\s]", "");
    }
}
