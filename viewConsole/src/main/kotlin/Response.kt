package com.example

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val sourceText: String,
    val translatedText: String)
