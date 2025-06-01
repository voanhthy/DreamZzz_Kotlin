package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.User
import de.syntax_institut.androidabschlussprojekt.data.repository.AuthServiceRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import java.util.Date

class UserProfileViewModel(
    authServiceRepoInterface: AuthServiceRepoInterface
): ViewModel() {

    private val _emailFromAuth = MutableStateFlow<String?>(null)
    val emailFromAuth = _emailFromAuth.asStateFlow()

    private val _memberSinceFromAuth = MutableStateFlow<Date?>(null)
    val memberSinceFromAuth = _memberSinceFromAuth.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val userProfile: StateFlow<User?> = authServiceRepoInterface.authState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    // Vornamen aus Flow<User?> ableiten
    val firstName = authServiceRepoInterface.authState
        .map { user: User? ->
            user?.firstName
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )


    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _emailFromAuth.value = authServiceRepoInterface.getCurrentUserEmail()
                _memberSinceFromAuth.value = authServiceRepoInterface.getCurrentUserMemberSince()
            } catch (e: Exception) {
                // TODO: error
            } finally {
                _isLoading.value = false
            }
        }
    }
}