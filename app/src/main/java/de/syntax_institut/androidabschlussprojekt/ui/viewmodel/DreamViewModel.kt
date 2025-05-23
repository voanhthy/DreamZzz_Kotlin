package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDao
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DreamViewModel(
    private val dreamImageRepoInterface: DreamImageRepoInterface,
    private val dreamImagedao: DreamImageDao
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

    private val _selectedMood = MutableStateFlow<Mood>(Mood.GOOD)
    val selectedMood = _selectedMood.asStateFlow()

    val savedDreamImages: Flow<List<DreamImage>> = dreamImagedao.getAllItems()

    // Bild in Datenbank speichern
    fun saveDreamImage(dreamImage: DreamImage) {
        viewModelScope.launch {
            dreamImagedao.insert(dreamImage)
        }
    }

    // TODO: Stil integrieren
    fun fetchImage(prompt: String) {
        viewModelScope.launch {
            Log.d(TAG, "fetchImage aufgerufen mit Prompt: $prompt")
            try {

                // API Call über Repo
                val imageUrl = dreamImageRepoInterface.generateImage(prompt)
                Log.d(TAG, "Bild-URL vom Repo: $imageUrl")

                if (imageUrl != null) {
                    val dream = DreamImage(
                        url = imageUrl,
                        prompt = prompt
                    )
                    _dreamImage.value = dream
                    saveDreamImage(dream)       // Bild speichern
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error im ViewModel: $e")
            }
        }
    }

    // Textfield Titel
    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    // Textfield Beschreibung
    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    // DreamCategory
    fun updateDreamCategory(category: DreamCategory) {
        _selectedDreamCategory.value = category
    }

    fun setMood(mood: Mood) {
        _selectedMood.value = mood
    }
}