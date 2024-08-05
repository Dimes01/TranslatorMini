package com.example.tink_lab.models;

/**
 * @param code краткое название языка, используемое в качестве параметра для запроса к внешнему сервису
 * @param fullName полное название языка
 */
public record Language(String code, String fullName) {
}
