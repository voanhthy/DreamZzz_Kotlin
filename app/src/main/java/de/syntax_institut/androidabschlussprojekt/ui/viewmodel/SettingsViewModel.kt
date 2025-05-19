package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.dataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Schlüssel für Zugriff auf einen Wert
private val DATASTORE_NOTIFCATION_KEY = booleanPreferencesKey("notificationsOn")
private val DATASTORE_DARKMODE_KEY = booleanPreferencesKey("isDarkMode")

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    // DataStore-Instanz
    private val dataStore = application.dataStore

    private val isNotificationOnFlow = dataStore.data
            .map { preferences ->
                preferences[DATASTORE_NOTIFCATION_KEY] ?: false
            }

    // sammelt Daten vom Flow, solange ViewModel existiert (scope), gibt Daten aus, solange mind ein Beobachter den StateFlow beobachtet (started), Startwert ist false (initialValue)
    val isNotificationOnStateFlow = isNotificationOnFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    private val isDarkModeFlow = dataStore.data
        .map { preferences ->
            preferences[DATASTORE_DARKMODE_KEY] ?: false
        }

    val isDarkModeStateFlow = isDarkModeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    fun toggleNotificationOn() {
        viewModelScope.launch {
            dataStore.edit { values ->
                val current = values[DATASTORE_NOTIFCATION_KEY] ?: false
                values[DATASTORE_NOTIFCATION_KEY] = !current
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            dataStore.edit { values ->
                val current = values[DATASTORE_DARKMODE_KEY] ?: false
                values[DATASTORE_DARKMODE_KEY] =  !current
            }
        }
    }
}