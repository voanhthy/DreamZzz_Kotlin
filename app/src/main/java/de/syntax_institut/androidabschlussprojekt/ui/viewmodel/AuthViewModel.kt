package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.AuthServiceRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import de.syntax_institut.androidabschlussprojekt.R

class AuthViewModel(
    private val authServiceRepoInterface: AuthServiceRepoInterface
): ViewModel() {

    private val TAG = "AuthViewModel"

    // isLoggedIn = true, sobald FirebaseAuth.authStateListener ein User-Objekt liefert
    val isLoggedIn = authServiceRepoInterface.authState
        .map { currentUser ->
            currentUser != null
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )


    private val _showRegister = MutableStateFlow(false)
    val showRegister = _showRegister.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess = _registrationSuccess.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    // Verwendet Int? für String-Ressourcen-IDs
    private val _errorMessageResId = MutableStateFlow<Int?>(null)
    val errorMessageResId: StateFlow<Int?> = _errorMessageResId.asStateFlow()


    // Eingabefelder
    private val _firstNameInput = MutableStateFlow("")
    val firstNameInput = _firstNameInput.asStateFlow()

    private val _lastNameInput = MutableStateFlow("")
    val lastNameInput = _lastNameInput.asStateFlow()

    private val _emailInput = MutableStateFlow("")
    val emailInput = _emailInput.asStateFlow()

    private val _passwordInput = MutableStateFlow("")
    val passwordInput = _passwordInput.asStateFlow()

    private val _passwordRepeatInput = MutableStateFlow("")
    val passwordRepeatInput = _passwordRepeatInput.asStateFlow()
    ///


    private val _showPasswordHint = MutableStateFlow(false)
    val showPasswordHint = _showPasswordHint.asStateFlow()

    private val _showPasswordRepeatHint = MutableStateFlow(false)
    val showPasswordRepeatHint = _showPasswordRepeatHint.asStateFlow()

    private val _showEmailError = MutableStateFlow(false)
    val showEmailError = _showEmailError.asStateFlow()

    private val _showPasswordError = MutableStateFlow(false)
    val showPasswordError = _showPasswordError.asStateFlow()

    private val _showPasswordErrorRepeat = MutableStateFlow(false)
    val showPasswordErrorRepeat = _showPasswordErrorRepeat.asStateFlow()

    private val _showNameError = MutableStateFlow(false)
    val showNameError = _showNameError.asStateFlow()

    fun updateEmailInput(value: String) {
        _emailInput.value = value
        _showEmailError.value = false

//        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches() && value.isNotBlank()) {
//            _showEmailError.value = true
//        } else if (value.isBlank()) {
//            _showEmailError.value = true
//        }
    }

    fun updatePasswordInput(value: String) {
        _passwordInput.value = value
        _showPasswordError.value = false
        _showPasswordErrorRepeat.value = false

//        if (value.isBlank() || value.length < 6) {
//            _showPasswordError.value = true
//        }
//
//        if (_passwordRepeatInput.value.isNotBlank() && _passwordRepeatInput.value != value) {
//            _showPasswordErrorRepeat.value = true
//        } else if (_passwordRepeatInput.value.isNotBlank() && _passwordRepeatInput.value == value) {
//            _showPasswordErrorRepeat.value = false
//        }
    }

    fun updatePasswordRepeatInput(value: String) {
        _passwordRepeatInput.value = value
        _showPasswordErrorRepeat.value = false

        if (value != _passwordInput.value) {
            _showPasswordErrorRepeat.value = true
        } else if (value.isBlank()) {
            _showPasswordErrorRepeat.value = true
        }
    }

    fun updateFirstNameInput(value: String) {
        _firstNameInput.value = value
    }

    fun updateLastNameInput(value: String) {
        _lastNameInput.value = value
    }

    suspend fun loginOrRegister() {
        val firstName = _firstNameInput.value
        val lastName = _lastNameInput.value
        val email = _emailInput.value
        val password = _passwordInput.value
        val passwordRepeat = _passwordRepeatInput.value


        if (_showRegister.value) {
            // mit AuthService registrieren
            if (validateRegisterInputs(firstName, lastName, email, password, passwordRepeat)) {
                viewModelScope.launch {
                    val result = authServiceRepoInterface.register(firstName, lastName, email, password)
                    result.onFailure { e ->
                        Log.e("AuthViewModel", "loginOrRegister: ", e)
                    }
                }
            }
        } else {
            // Login mit AuthService
            authServiceRepoInterface.login(email, password)
        }
    }

    private fun validateRegisterInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordRepeat: String
    ): Boolean {

        resetErrorStates()

        var isValid = true

        // wenn email leer ist oder kein gültiges email pattern hat dann zeigen wir dem nutzer einen hinweis an
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _showEmailError.value = true
            isValid = false
        }
        // wenn das password leer ist oder kürzer als 6 zeichen zeigen wir dem nutzer einen hinweis an
        if (password.isBlank() || password.length < 6) {
            _showPasswordError.value = true
            isValid = false
        }

        // wenn das wiederholte password nicht gleich ist dann zeigen wir dem nutzer einen hinweis an
        if (passwordRepeat != password) {
            _showPasswordErrorRepeat.value = true
            isValid = false
        }

        if (firstName.isEmpty() && lastName.isEmpty()) {
            _showNameError.value = true
            isValid = false
        }

        return isValid
    }

    fun registerUser() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessageResId.value = null     // vorherige Fehler löschen

            // trim(), um white space zu entfernen
            val firstName = _firstNameInput.value.trim()
            val lastName = _lastNameInput.value.trim()
            val email = _emailInput.value.trim()
            val password = _passwordInput.value
            val passwordRepeat = _passwordRepeatInput.value

            var isValid = true

            // Eingabe validieren
            if (!validateRegisterInputs(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    passwordRepeat = passwordRepeat
                )) {
                _isLoading.value = false        // Ladezustand beenden
                return@launch       // abbrechen
            }

            // Registrierung aufrufen
            val result = authServiceRepoInterface.register(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password
            )

            _isLoading.value = false            // Ladezustand beenden

            result.onSuccess {
                _registrationSuccess.value = true
                Log.d(TAG, "Registrierung erfolgreich für $email")
            }.onFailure { e ->
                val errorMessage = e.message ?: "Unbekannter Fehler"

                // TODO: errorResId für verschiedene errors

                Log.d(TAG, "Registrierung fehlgeschlagen")
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessageResId.value = null     // vorherige Fehler löschen
            resetErrorStates()

            val email = _emailInput.value.trim()
            val password = _passwordInput.value
            var isValid = true

            // Hinweis, wenn Email leer oder kein gültiges Email pattern
            if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _showEmailError.value = true
                isValid = false
            }

            // Hinweis, wenn Passwort leer oder kürzer als 6 Zeichen
            if (password.isBlank() || password.length < 6) {
                _showPasswordHint.value = true
                isValid = false
            }

            if (!isValid) {
                _isLoading.value = false    // Ladezustand beenden, da Validierung fehlgeschlagen
                return@launch               // abbrechen
            }

            val result = authServiceRepoInterface.login(email, password)
            _isLoading.value = false

            result.onSuccess {
                _loginSuccess.value = true
                Log.d(TAG, "User erfolgreich angemeldet: $email")
                resetInputs()               // Eingabefelder nach erfolgreichem Login leeren
                resetErrorStates()          // Fehler zurücksetzen
            }.onFailure {
                _errorMessageResId.value = R.string.error_login_failed_credentials
                _loginSuccess.value = false     // bleibt falsch, wenn Login fehlschlägt
            }
        }
    }

    fun tryLogin() {
        val email = _emailInput.value
        val password = _passwordInput.value
        var isValid = true

        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _showEmailError.value = true
            isValid = false
        }

        if (password.isBlank() || password.length < 6) {
            _showPasswordError.value = true
            isValid = false
        }

        if (!isValid) return

        loginUser()
    }

    fun logoutUser(): Result<Unit> {
        return try {
            authServiceRepoInterface.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun resetErrorStates() {
        _showEmailError.value = false
        _showPasswordError.value = false
        _showPasswordErrorRepeat.value = false
    }

    private fun resetInputs() {
        _firstNameInput.value = ""
        _lastNameInput.value = ""
        _emailInput.value = ""
        _passwordInput.value = ""
        _passwordRepeatInput.value = ""
    }

    fun resetAuthStates() {
        _registrationSuccess.value = false
        _loginSuccess.value = false
    }
}