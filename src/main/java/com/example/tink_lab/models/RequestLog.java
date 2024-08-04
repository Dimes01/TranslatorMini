package com.example.tink_lab.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <b>Используется для сохранения информации о запросе в БД.</b>
 * <p>Сохраняется следующая информация:</p>
 * <li>IP (IP-адрес)</li>
 * <li>SourceText (Текст на исходном языке)</li>
 * <li>TranslatedText (Переведённый текст)</li>
 */
public class RequestLog {
    /**
     * @param ip IP-адрес
     * @param sourceText Текст на исходном языке
     * @param translatedText Переведённый текст
     */
    public RequestLog(String ip, String sourceText, String translatedText) {
        this.ip = ip;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
    }

    private final String sourceText;
    private final String translatedText;
    private final String ip;

    /**
     * @return IP-адрес
     */
    @JsonProperty("ip")
    public String GetIP() { return ip; }

    /**
     * @return Текст на исходном языке
     */
    @JsonProperty("sourceText")
    public String GetSourceText() { return sourceText; }

    /**
     * @return Переведённый текст
     */
    @JsonProperty("translatedText")
    public String GetTranslatedText() { return translatedText; }
}
