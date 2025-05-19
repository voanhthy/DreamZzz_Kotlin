package de.syntax_institut.androidabschlussprojekt.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "dreams")
data class DreamImage(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var url: String,
//    val imageData: Data?
//    var date: Date,
    var prompt: String,
//    var title: String?,
//    var interpretation: String?,
//    var mood: String?,
//    var typeOfDream: String?
)