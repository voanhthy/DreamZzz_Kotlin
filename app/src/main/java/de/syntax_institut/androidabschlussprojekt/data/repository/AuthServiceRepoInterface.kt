package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.model.User
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

interface AuthServiceRepoInterface {

    val authState: StateFlow<User?>

    suspend fun register(firstName: String, lastName: String, email: String, password: String): Result<Unit>

    suspend fun login(email: String, password: String): Result<Unit>

    fun logout()

    fun getCurrentUserId(): String?

    fun getCurrentUserEmail(): String?

    fun getCurrentUserMemberSince(): Date?

    suspend fun createUserProfile(user: User)

    suspend fun updateUserProfile(user: User)
}