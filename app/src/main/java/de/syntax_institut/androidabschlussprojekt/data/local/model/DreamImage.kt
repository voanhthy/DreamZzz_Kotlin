package de.syntax_institut.androidabschlussprojekt.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import java.util.Date

@Entity(tableName = "dreams")
data class DreamImage(
    @PrimaryKey         // ohne autoGenerate, da String-ID von Firestore
    @DocumentId         // wichtig, damit Firestore dieses Feld als Dokument-ID verwendet
    var id: String = "",
    var url: String = "",
//    val imageData: Data?
    var date: Date = Date(),
    var prompt: String = "",
    var title: String? = "Unbekannter Traum",
    var interpretation: String? = "Keine Traumdeutung vorhanden",
    var mood: Mood = Mood.GOOD,
    var typeOfDream: DreamCategory = DreamCategory.NORMAL,
    var imageStyle: ImageStyle = ImageStyle.WATERCOLOR
)