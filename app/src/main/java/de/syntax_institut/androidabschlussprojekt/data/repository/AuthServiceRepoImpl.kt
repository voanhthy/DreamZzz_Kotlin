package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.syntax_institut.androidabschlussprojekt.data.local.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.Date

class AuthServiceRepoImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthServiceRepoInterface {

    private val TAG = "AuthServiceRepoImpl"


    private val _authState: MutableStateFlow<User?> = MutableStateFlow(null)
    override val authState: StateFlow<User?> = _authState

    init {
        firebaseAuth.addAuthStateListener { auth ->
            val authUser = auth.currentUser

            Log.d(TAG, "auth: User ${authUser?.email}")

            if (authUser == null) {
                // ausgeloggt
                _authState.value = null
                Log.d(TAG, "Kein User mit UID: ${authUser?.uid}")
            } else {
                // eingeloggt
                firestore.collection("users").document(authUser.uid).get().addOnSuccessListener { userDoc ->
                    _authState.value = userDoc.toObject(User::class.java)
                }
                Log.d(TAG, "User von Firestore geladen: ${authUser.email}")
            }
        }
    }

    override suspend fun register(firstName: String, lastName: String, email: String, password: String): Result<Unit> {
        try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .await()
            val authUser = authResult.user!!
            val newUser = User(
                userId = authUser.uid,
                firstName = firstName,
                lastName = lastName,
                email = email,
                registeredOn = Date()
            )

            // wenn wir uns registriert haben -> user im firestore anlegen
            firestore.collection("users").document(authUser.uid).set(newUser).await()
            Log.d(TAG, "Neuer User im Firestore registriert mit UID: ${authUser.uid}")
            return Result.success(Unit)
        } catch (e: Exception) {
            Log.d(TAG, "Registrierung fehlgeschlagen")
            return Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
            Log.d(TAG, "User erfolgreich eingeloggt: ${email}")
            return Result.success(Unit)
        } catch (e: Exception) {
            Log.d(TAG, "Login fehlgeschlagen")
            return Result.failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
        Log.d(TAG, "User erfolgreich ausgeloggt")
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    override fun getCurrentUserMemberSince(): Date? {
        return firebaseAuth.currentUser?.metadata?.creationTimestamp?.let { Date(it) }
    }

    override suspend fun createUserProfile(user: User) {
        val uid = user.userId

        if (uid.isBlank()) {
            throw IllegalArgumentException("User ID darf nicht leer sein")
        }
        firestore.collection("users").document(uid).set(user).await()
        Log.d(TAG, "User Profil erstellt für UID: $uid")
    }

    override suspend fun updateUserProfile(user: User) {
        val uid = user.userId

        if (uid.isBlank()) {
            throw IllegalArgumentException("User ID darf nicht leer sein")
        }
        firestore.collection("users").document(uid).set(user).await()
        Log.d(TAG, "User Profil erstellt für UID: $uid")
    }
}