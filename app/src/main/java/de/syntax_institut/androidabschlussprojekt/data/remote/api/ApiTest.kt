package de.syntax_institut.androidabschlussprojekt.data.remote.api

import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = DreamImageApi.retrofitService
    val prompt = "a green banana"

    try {
        val response = api.generateImage(authHeader = "Bearer ${BuildConfig.API_KEY}",
            request = DreamImageRequest(prompt, model = "dall-e-2", n = 1, size = "1024x1024"))
        println("API Response: ${response}")
    } catch (e: Exception) {
        println("API Fehler: ${e.message}")
    }
}