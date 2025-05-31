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

    companion object {
        @Volatile
        private var instance: AuthServiceRepoImpl? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: AuthServiceRepoImpl(
                        firebaseAuth = FirebaseAuth.getInstance(),
                        firestore = FirebaseFirestore.getInstance()
                    ).also { instance = it }
            }
    }


    private val _authState: MutableStateFlow<User?> = MutableStateFlow(null)
    val authState: StateFlow<User?> = _authState

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

    override fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
        Log.d(TAG, "User erfolgreich eingeloggt: ${email}")
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

//    override fun getCurrentUserMemberSince(): Date? {
//
//    }
}