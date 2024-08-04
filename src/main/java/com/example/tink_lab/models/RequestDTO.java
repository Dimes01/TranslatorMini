package com.example.tink_lab.models;

public class RequestDTO {
    private final String sourceText;
    private final String translatedText;

    public RequestDTO(String sourceText, String translatedText) {
        this.sourceText = sourceText;
        this.translatedText = translatedText;
    }

    public String getSourceText() { return sourceText; }

    public String getTranslatedText() { return translatedText; }
}
