package org.langapp.string;

public class StringUtil {
    public String normalized(String input) {
        return input
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}\\s]", "");
    }
}
