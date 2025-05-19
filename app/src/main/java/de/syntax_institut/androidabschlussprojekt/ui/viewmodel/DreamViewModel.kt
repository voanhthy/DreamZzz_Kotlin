package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DreamViewModel(
    private val dreamImageRepoInterface: DreamImageRepoInterface
): ViewModel() {

    private val TAG = "DreamViewModel"


    // StateFlows für UI-Komponenten
    private val _dreamImage = MutableStateFlow<DreamImage?>(null)
    val dreamImage = _dreamImage.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _selectedDreamCategory = MutableStateFlow<DreamCategory>(DreamCategory.NORMAL)
    val selectedDreamCategory = _selectedDreamCategory.asStateFlow()



    // TODO: Stil integrieren
    fun fetchImage(prompt: String) {
        viewModelScope.launch {
            Log.d(TAG, "fetchImage aufgerufen mit Prompt: $prompt")
            try {

                // API Call über Repo
                val imageUrl = dreamImageRepoInterface.generateImage(prompt)
                Log.d(TAG, "Bild-URL vom Repo: $imageUrl")

                if (imageUrl != null) {
                    _dreamImage.value = DreamImage(
                        url = imageUrl,
                        prompt = prompt
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error im ViewModel: $e")
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun updateDreamCategory(category: DreamCategory) {
        _selectedDreamCategory.value = category
    }
}