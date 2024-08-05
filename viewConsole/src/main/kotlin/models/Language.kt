package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Language(val code: String, val fullName: String)
