package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.api.DreamImageApi
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamImageRequest

// alles was mit api zu tun hat

class DreamImageRepoApiImpl(
    private val dreamImageApi: DreamImageApi
): DreamImageRepoInterface {

    override suspend fun generateImage(prompt: String): String? {
        return try {
            val response = dreamImageApi.generateImage(
                authHeader = "Bearer ${BuildConfig.API_KEY}",
                request = DreamImageRequest(prompt, model = "dall-e-2", n = 1, size = "1024x1024"))

        } catch (e: Exception) {
            Log.e("DreamImageRepo", "Error: $e")
        }
    }
}