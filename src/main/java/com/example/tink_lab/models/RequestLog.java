package com.example.tink_lab.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestLog {
    public RequestLog(String sourceText, String translatedText, String ip) {
        this.sourceText = sourceText;
        this.translatedText = translatedText;
        this.ip = ip;
    }
    private final String sourceText;
    private final String translatedText;
    private final String ip;

    @JsonProperty("sourceText")
    public String GetSourceText() { return sourceText; }
    @JsonProperty("translatedText")
    public String GetTranslatedText() { return translatedText; }
    @JsonProperty("ip")
    public String GetIP() { return ip; }
}
