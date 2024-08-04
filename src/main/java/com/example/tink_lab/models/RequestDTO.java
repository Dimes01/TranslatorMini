package com.example.tink_lab.models;


/**
 * <b>Используется как модель для ответа</b>
 * @param sourceText Текст на исходном языке
 * @param translatedText Переведённый текст
 */
public record RequestDTO(String sourceText, String translatedText) {
}
