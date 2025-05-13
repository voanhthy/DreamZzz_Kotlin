package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class DreamImageRequest(
    val prompt: String,
    val model: String,
    val n: Int,
    val size: String
)
