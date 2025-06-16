package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDao
import de.syntax_institut.androidabschlussprojekt.navigation.DreamDetailRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DreamDetailViewModel(
    savedStateHandle: SavedStateHandle,
    dreamImagedao: DreamImageDao
): ViewModel() {

    private val args = savedStateHandle.toRoute<DreamDetailRoute>()

    // aus Datenbank über id holen
    val dreamStateFlow = dreamImagedao.getItemById(args.id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )
}