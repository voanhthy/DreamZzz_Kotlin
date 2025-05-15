package de.syntax_institut.androidabschlussprojekt.data.repository

// alle Funktionen vom Repository eintragen ohne Code, den die Funktionen ausführen
interface DreamImageRepoInterface {
    suspend fun generateImage(prompt: String): String?
}