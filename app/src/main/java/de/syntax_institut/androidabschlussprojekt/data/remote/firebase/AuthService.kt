package de.syntax_institut.androidabschlussprojekt.data.remote.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.syntax_institut.androidabschlussprojekt.data.local.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

class AuthService {

    private val TAG = "AuthService"

    companion object {
        @Volatile
        private var instance: AuthService? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: AuthService().also { instance = it }
            }
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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

    suspend fun register(firstName: String, lastName: String, email: String, password: String): Result<Unit> {
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

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
        Log.d(TAG, "User erfolgreich eingeloggt: ${email}")
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}