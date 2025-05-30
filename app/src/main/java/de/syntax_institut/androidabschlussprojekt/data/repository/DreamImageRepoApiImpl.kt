package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.remote.api.APIService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest

// alles was mit api zu tun hat

class DreamImageRepoApiImpl(
    private val apiService: APIService
): DreamImageRepoInterface {

    override suspend fun generateImage(prompt: String, imageStyle: ImageStyle): String? {

        val styleDescription = imageStyle.description()     // Stilbeschreibung abrufen
        val promptWithStyle = "$prompt, $styleDescription"         // Prompt mit Stilbeschreibung

        return try {
            val response = apiService.generateImage(
                authHeader = "Bearer ${BuildConfig.API_KEY}",
                request = DreamImageRequest(promptWithStyle, model = "dall-e-2", n = 1, size = "1024x1024"))

            if (response.isSuccessful && response.body() != null) {
                return response.body()?.data?.firstOrNull()?.url
            } else {
                Log.e("DreamImageRepo", "Fehlerhafte Antwort vom Server: ${response.code()} - ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("DreamImageRepo", "Error: $e")
            return null
        }
    }
}