package com.example

import com.example.models.Request
import com.example.models.Response
import com.example.models.Language
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


fun main() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    printInfo()
    while (true) {
        print("Select command: ")
        val key = readln()
        if (key == "-h") printInfo()
        else if (key == "-t") postTranslate(client)
        else if (key == "-l") getPossibleLanguages(client)
        else if (key == "-s") getLogs(client)
        else if (key == "-r") break
        else println("Incorrect command!")
    }

    client.close()
}

fun printInfo() {
    println("-h - help")
    println("-t - translate")
    println("-l - possible languages")
    println("-s - show logs (previous translates)")
    println("-r - return")
}

fun getPossibleLanguages(client: HttpClient) {
    runBlocking {
        try {
            val responseBody: List<Language> = client.get("http://localhost:8080/translate/languages").body()
            for (lang in responseBody) {
                println("${lang.code} - ${lang.fullName}")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

fun postTranslate(client: HttpClient) {
    var isTranslate = true
    do {
        print("Enter a text to translate: ")
        val text = readln()
        print("Enter a code of source language: ")
        val sourceLang = readln()
        print("Enter a code of target language: ")
        val targetLang = readln()
        val requestData = Request(text, sourceLang, targetLang)
        runBlocking {
            try {
                val response: HttpResponse = client.post("http://localhost:8080/translate") {
                    contentType(ContentType.Application.Json)
                    setBody(requestData)
                }
                val responseBody: Response = response.body()
                println("Translated Text: ${responseBody.translatedText}")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
        print("Would you like to break operation? (Y/n or another text)")
        val confirm = readln()
        if (confirm == "Y") {
            isTranslate = false;
        }
    } while (isTranslate)
}

fun getLogs(client: HttpClient) {
    runBlocking {
        try {
            val responseBody: List<Response> = client.get("http://localhost:8080/translate/get").body()
            println("----")
            for (log in responseBody) {
                println("Source text: ${log.sourceText}")
                println("Translated text: ${log.translatedText}")
                println("----")
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}