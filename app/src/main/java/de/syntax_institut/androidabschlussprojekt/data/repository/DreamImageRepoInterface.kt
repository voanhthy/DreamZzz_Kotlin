package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import kotlinx.coroutines.flow.Flow

// alle Funktionen vom Repository eintragen ohne Code, den die Funktionen ausführen
interface DreamImageRepoInterface {

    suspend fun generateImage(prompt: String, imageStyle: ImageStyle): String?
}