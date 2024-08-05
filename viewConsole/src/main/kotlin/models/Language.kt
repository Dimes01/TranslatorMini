package com.example.models

import kotlinx.serialization.Serializable

/**
 * Содержит информацию о конкретном языке, используемым сервисом для перевода
 * @param code краткое название языка, используемое в качестве параметра запроса
 * @param fullName полное название языка
 */
@Serializable
data class Language(val code: String, val fullName: String)
