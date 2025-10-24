package org.langapp.documents.dto.processor;

public sealed interface Unit permits Phrase, Word {
    int id();
    String rawContent();
    Unit withId(int id);
}
