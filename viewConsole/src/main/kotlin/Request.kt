package com.example

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val text: String,
    val sourceLanguage: String,
    val targetLanguage: String)
