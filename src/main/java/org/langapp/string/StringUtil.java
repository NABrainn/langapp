package org.langapp.string;

public class StringUtil {
    public String normalized(String input) {
        return input
                .trim()
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}\\s]", "");
    }
}
