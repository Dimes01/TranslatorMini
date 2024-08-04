package com.example.tink_lab.models;

/**
 * <b>Используется как модель данных для запроса на перевод.</b>
 * <p>Сохраняется следующая информация:</p>
 * <li>Text (текст на исходном языке)</li>
 * <li>SourceLanguage (исходный язык)</li>
 * <li>TargetLanguage (язык для перевода)</li>
 */
public class RequestTranslate {
    /**
     * @param text Текст на исходном языке
     * @param sourceLanguage Исходный язык
     * @param targetLanguage Язык для перевода
     */
    public RequestTranslate(String text, String sourceLanguage, String targetLanguage) {
        this.text = text;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }
    private final String text;
    private final String sourceLanguage;
    private final String targetLanguage;

    /**
     * @return Текст на исходном языке
     */
    public String GetText() { return text; }

    /**
     * @return Исходный язык
     */
    public String GetSourceLanguage() { return sourceLanguage; }

    /**
     * @return Язык для перевода
     */
    public String GetTargetLanguage() { return targetLanguage; }
}
