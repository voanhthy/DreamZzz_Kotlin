package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class DreamImageRequest(
    var prompt: String,
    var model: String,
    var n: Int,
    var size: String
)
