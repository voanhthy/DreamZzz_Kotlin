package de.syntax_institut.androidabschlussprojekt.data.repository

interface DreamAnalyzeRepoInterface {
    suspend fun analyzeImage(prompt: String): String
}