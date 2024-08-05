package com.example.models

import kotlinx.serialization.Serializable

/**
 * Содержит информацию об ответе на запрос на перевод
 * @param sourceText текст на исходном языке
 * @param translatedText переведенный текст
 */
@Serializable
data class Response(
    val sourceText: String,
    val translatedText: String)
