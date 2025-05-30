package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDao
import de.syntax_institut.androidabschlussprojekt.ui.PreviewRoute

class PreviewViewModel(
    savedStateHandle: SavedStateHandle,
    dreamImagedao: DreamImageDao
): ViewModel() {

    private val args = savedStateHandle.toRoute<PreviewRoute>()

    // aus Datenbank über id holen
//    val dreamStateFlow = dreamImagedao.getItemById(args.id)
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(),
//            initialValue = null
//        )

    val url = args.url
}