package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class DreamImageResponse(
    val data: List<ImageResponse>
)

data class ImageResponse(
    val url: String
)