package com.example.tink_lab.models;

public class RequestTranslate {
    public RequestTranslate(String text, String sourceLanguage, String targetLanguage) {
        this.text = text;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }
    private final String text;
    private final String sourceLanguage;
    private final String targetLanguage;

    public String GetText() { return text; }
    public String GetSourceLanguage() { return sourceLanguage; }
    public String GetTargetLanguage() { return targetLanguage; }
}
