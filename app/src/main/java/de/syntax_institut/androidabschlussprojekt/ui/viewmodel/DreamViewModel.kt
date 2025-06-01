package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.icu.util.Calendar
import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDao
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamAnalyzeRepoInterface
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamFirestoreRepoInterface
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import de.syntax_institut.androidabschlussprojekt.utils.helper.DateUtils.dateWithoutTimestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class DreamViewModel(
    private val dreamImageRepoInterface: DreamImageRepoInterface,
    private val dreamAnalyzeRepoInterface: DreamAnalyzeRepoInterface,
    private val dreamFirestoreRepoInterface: DreamFirestoreRepoInterface,
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

    private val _selectedImageStyle = MutableStateFlow<ImageStyle>(ImageStyle.WATERCOLOR)
    val selectedImageStyle = _selectedImageStyle.asStateFlow()

    private val _date = MutableStateFlow(Date())
    val date = _date.asStateFlow()

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult = _analysisResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val savedDreamImages: Flow<List<DreamImage>> = dreamImagedao.getAllItems()

    val datesWithDreams = dreamFirestoreRepoInterface.getDatesWithDreams()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptySet()
        )

    private val _selectedDate = MutableStateFlow(dateWithoutTimestamp(Date()))
    val selectedDate = _selectedDate.asStateFlow()

    val dreamsForSelectedDate = _selectedDate
        .flatMapLatest { date ->
            dreamFirestoreRepoInterface.getDreamsForDate(
                date = _selectedDate.value
            )
        }

    val dreamCount = savedDreamImages
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )


    // Bild in Datenbank speichern
    fun saveDreamImage(dreamImage: DreamImage) {
        viewModelScope.launch {
            dreamImagedao.insert(dreamImage)
        }
    }

    // TODO: Stil integrieren
    fun fetchImage(prompt: String) {

        // für Ladevorgang
        _isLoading.value = true
        _dreamImage.value = null        // vorheriges Bild zurücksetzen
        Log.d(TAG, "Bild wird geladen - isLoading ist true")
        val startTime = System.currentTimeMillis()

        viewModelScope.launch {
            Log.d(TAG, "fetchImage aufgerufen mit Prompt: $prompt")
            try {

                // API Call über Repo
                val imageUrl = dreamImageRepoInterface.generateImage(prompt, selectedImageStyle.value)
                Log.d(TAG, "Bild-URL vom Repo: $imageUrl")

                if (imageUrl != null) {
                    val dream = DreamImage(
                        id = UUID.randomUUID().toString(),
                        url = imageUrl,
                        prompt = prompt,
                        mood = _selectedMood.value,
                        typeOfDream = _selectedDreamCategory.value,
                        title = _title.value,
                        date = _date.value,
                        interpretation = null
                    )

                    Log.d(TAG, "Versuch _dreamImage.value ui $dream zu setzen")
                    _dreamImage.value = dream   // State für DetailScreen aktualisieren
                    Log.d(TAG, "_dreamImage.value ist jetzt ${_dreamImage.value?.url ?: "NULL"}")
                    saveDreamImage(dream)       // Bild speichern
                    dreamFirestoreRepoInterface.addDream(dream)
                }
            } catch (e: Exception) {
                e(TAG, "Error im ViewModel: $e")
            } finally {
                val elapsedTime = System.currentTimeMillis() - startTime
                val minDisplayTime = 1500L

                if (elapsedTime < minDisplayTime) {
                    delay(minDisplayTime - elapsedTime)
                }

                _isLoading.value = false
                Log.d(TAG, "Bild erfolgreich geladen - isLoading ist false")
            }
        }
    }

    fun analyzeImage(prompt: String) {
        viewModelScope.launch {
            Log.d(TAG, "analyzeImage aufgerufen mit Prompt: $prompt")
            try {
                // API Call über Repo
                val interpretation = dreamAnalyzeRepoInterface.analyzeImage(prompt)
                Log.d(TAG, "Traumdeutung vom Repo: $interpretation")
                // Interpretation speichern
                _analysisResult.value = interpretation

                if (interpretation != null) {
                    val dreamAnalysis = DreamImage(
                        url = "",
                        date = _date.value,
                        prompt = prompt,
                        mood = _selectedMood.value,
                        typeOfDream = _selectedDreamCategory.value
                    )
                }
            } catch (e: Exception) {
                e(TAG, "Error im ViewModel: $e")
            }
        }
    }

    // StateFlows aktualisieren
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

    // Mood
    fun setMood(mood: Mood) {
        _selectedMood.value = mood
    }

    // Date
    fun updateDate(newDate: Date) {
        _date.value = newDate
    }

    // ImageStyle
    fun updateImageStyle(newStyle: ImageStyle) {
        _selectedImageStyle.value = newStyle
    }

    // Bild speichern
    fun saveImage() {
        viewModelScope.launch {
            _dreamImage.value?.let { dream ->
                dreamImagedao.insert(dream)
                Log.d(TAG, "Bild wurde gespeichert: $dream")
            } ?: Log.w(TAG, "Kein Bild zum Bild vorhanden")
        }
    }

    fun resetDreamImage() {
        _dreamImage.value = null
        Log.d(TAG, "dreamImage wurde auf null zurückgesetzt")
    }

    fun deleteDreamImage(dreamImage: DreamImage) {
        viewModelScope.launch {
            try {
                dreamImagedao.delete(dreamImage)
            } catch (e: Exception) {
                e(TAG, "Fehler beim Löschen", e)
            }
        }
    }

    // Datum ohne Uhrzeit anzeigen
//    private fun dateWithoutTimestamp(date: Date): Date {
//        val calendar = Calendar.getInstance().apply { time = date }
//
//        // für den Vergleich Std, Min, Sek, Millisek auf 0 setzen
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
//        calendar.set(Calendar.MINUTE, 0)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//        return  calendar.time
//    }

    fun updateSelectedDate(date: Date) {
        _selectedDate.value = dateWithoutTimestamp(date)
    }


}