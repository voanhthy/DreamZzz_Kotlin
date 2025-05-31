package de.syntax_institut.androidabschlussprojekt.data.repository

interface AuthServiceRepoInterface {

    suspend fun register(firstName: String, lastName: String, email: String, password: String): Result<Unit>

    fun login(email: String, password: String)

    fun logout()

    fun getCurrentUserId(): String?

    fun getCurrentUserEmail(): String?
}