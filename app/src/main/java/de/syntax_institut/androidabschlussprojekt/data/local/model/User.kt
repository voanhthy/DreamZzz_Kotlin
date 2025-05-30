package de.syntax_institut.androidabschlussprojekt.data.local.model

import java.util.Date
import java.util.UUID


// Firebase Datenklassen brauchen Standardwerte, sonst Fehler
data class User(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val registeredOn: Date = Date()
)