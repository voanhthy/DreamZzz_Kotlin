package de.syntax_institut.androidabschlussprojekt.data.local.model

import java.util.Date

data class DreamImage(
    var id: String,
    var url: String,
//    val imageData: Data?
    var date: Date,
    var prompt: String,
    var title: String?,
    var interpretation: String?,
    var mood: String?,
    var typeOfDream: String?
)