package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val text: String,
    val sourceLanguage: String,
    val targetLanguage: String)
