package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.BuildConfig
import de.syntax_institut.androidabschlussprojekt.data.remote.api.APIServiceAnalyze
import de.syntax_institut.androidabschlussprojekt.data.remote.model.DreamAnalyzeRequest
import de.syntax_institut.androidabschlussprojekt.data.remote.model.UserMessage

class DreamAnalyzeRepoApiImpl (
    private val apiServiceAnalyze: APIServiceAnalyze
): DreamAnalyzeRepoInterface {

    override suspend fun analyzeImage(prompt: String): String {
        return try {
            val response = apiServiceAnalyze.analyzeImage(
                authHeader = "Bearer ${BuildConfig.API_KEY}",
                request = DreamAnalyzeRequest(
                    message = listOf(UserMessage(
                        role = "system",
                        content = "Du bist ein Traumdeuter. Analysiere und interpretiere Träume auf Grundlage psychologischer und symbolischer Bedeutungen."),
                        UserMessage(
                            role = "user",
                            content = prompt
                        )),
                    model = "gpt-4"
                )
            )
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!
                return result.choices.firstOrNull()?.message?.content ?: "Keine Antwort erhalten."
            } else {
                Log.e("DreamAnalyzeRepo", "Fehlerhafte Antwort vom Server: ${response.code()} - ${response.message()}")
                return "Fehler"
            }
        } catch (e: Exception) {
            return  "Fehler bei Analyze"
        }
    }
}