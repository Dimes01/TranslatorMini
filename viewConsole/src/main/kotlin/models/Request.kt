package com.example.models

import kotlinx.serialization.Serializable

/**
 * Содержит информацию о запросе на перевод
 * @param text текст на исходном языке
 * @param sourceLanguage исходный язык
 * @param targetLanguage язык для перевода
 */
@Serializable
data class Request(
    val text: String,
    val sourceLanguage: String,
    val targetLanguage: String)
