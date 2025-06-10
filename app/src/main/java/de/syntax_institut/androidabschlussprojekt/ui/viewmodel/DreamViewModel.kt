package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import android.R.attr.category
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
) : ViewModel() {

    private val TAG = "DreamViewModel"


    // StateFlows für UI-Komponenten
    private val _dreamImage = MutableStateFlow<DreamImage?>(null)
    val dreamImage = _dreamImage.asStateFlow()

    //// Eingabefelder zum generieren des Bildes
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
    ////

    private val _interpretation = MutableStateFlow("")
    val interpretation = _interpretation.asStateFlow()

    private val _analysisResult = MutableStateFlow<String?>(null)
    val analysisResult = _analysisResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _savedImage = MutableStateFlow<DreamImage?>(null)
    val savedImage = _savedImage.asStateFlow()

    private val _selectedDate = MutableStateFlow(dateWithoutTimestamp(Date()))
    val selectedDate = _selectedDate.asStateFlow()

    private val _isImageReady = MutableStateFlow(false)
    val isImageReady = _isImageReady.asStateFlow()


    //// Room
    val savedDreamImages: Flow<List<DreamImage>> = dreamImagedao.getAllItems()

    // bei jeder Änderung von _selectedDate wird automatisch eine neue Datenbankabfrage gestartet
    val dreamsForSelectedDate = _selectedDate
        .flatMapLatest { date ->
            dreamImagedao.getDreamsByDate(dateWithoutTimestamp(date))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val savedDreamImagesState = savedDreamImages
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    ////   Filter & Sortierung   ////
    private val _sortAsc = MutableStateFlow(true)
    val sortAsc = _sortAsc.asStateFlow()

    private val _selectedMoodsFilter = MutableStateFlow<List<Mood>>(emptyList())
    val selectedMoodsFilter = _selectedMoodsFilter.asStateFlow()

    private val _selectedCategoriesFilter = MutableStateFlow<List<DreamCategory>>(emptyList())
    val selectedCategoriesFilter = _selectedCategoriesFilter.asStateFlow()

    private val _selectedImageStylesFilter = MutableStateFlow<List<ImageStyle>>(emptyList())
    val selectedImageStylesFilter = _selectedImageStylesFilter.asStateFlow()


    val filteredAndSortedDreams = combine(
        _sortAsc, _selectedMoodsFilter, _selectedCategoriesFilter, _selectedImageStylesFilter
    ) { sortAsc, moods, categories, styles ->

        val moodsToFilter = if (moods.isEmpty()) null else moods
        val categoriesToFilter = if (categories.isEmpty()) null else categories
        val stylesToFilter = if (styles.isEmpty()) null else styles

        // Object für Quadruple
        object {
            val sortAscParam = sortAsc
            val moodsParam = moodsToFilter
            val categoriesParam = stylesToFilter
            val stylesParam = stylesToFilter
        }
    }.flatMapLatest { params ->
        dreamImagedao.getFilteredAndSortedDreams(
            moods = params.moodsParam,
            categories = params.categoriesParam,
            styles = params.stylesParam,
            sortAsc = params.sortAscParam
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


    //// Firestore
    val datesWithDreams = dreamFirestoreRepoInterface.getDatesWithDreams()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptySet()
        )

//    val dreamsForSelectedDate = _selectedDate
//        .flatMapLatest { date ->
//            dreamFirestoreRepoInterface.getDreamsForDate(
//                date = _selectedDate.value
//            )
//        }
    ////



    // Anzahl aller Träume für NightSky
    val dreamCount = savedDreamImages
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )


    // Bild + Interpretation generieren
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
                val imageUrl =
                    dreamImageRepoInterface.generateImage(prompt, selectedImageStyle.value)
                Log.d(TAG, "Bild-URL vom Repo: $imageUrl")

                val interpretation = dreamAnalyzeRepoInterface.analyzeImage(prompt)

                // DreamImage erstellen
                if (imageUrl != null) {
                    val newDream = DreamImage(
                        id = UUID.randomUUID().toString(),
                        url = imageUrl,
                        prompt = prompt,
                        mood = _selectedMood.value,
                        typeOfDream = _selectedDreamCategory.value,
                        title = if (_title.value.isEmpty()) "Unbekannter Traum" else _title.value,
                        date = dateWithoutTimestamp(_date.value),
                        interpretation = interpretation
                    )

                    Log.d(TAG, "Neues DreamImage erstellt: $newDream")

                    _dreamImage.value = newDream   // State für DetailScreen aktualisieren

                    Log.d(TAG, "_dreamImage.value ist jetzt ${_dreamImage.value?.url ?: "NULL"}")

                    // Dream in Firestore speichern
                    dreamFirestoreRepoInterface.addDream(newDream)
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
        val cleanDate = dateWithoutTimestamp(newDate)
        _date.value = cleanDate
        _selectedDate.value = cleanDate
    }

    // ImageStyle
    fun updateImageStyle(newStyle: ImageStyle) {
        _selectedImageStyle.value = newStyle
    }

    // Traum lokal speichern (Room)
    fun saveImage() {
        viewModelScope.launch {
            _dreamImage.value?.let { dream ->
                dreamImagedao.insert(dream)
                _savedImage.value = dream
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

    fun updateSelectedDate(date: Date) {
        _selectedDate.value = dateWithoutTimestamp(date)
    }

    //// Spracheingabe
    // transkibierter Text
    fun appendTranscribedDescription(text: String) {
        _description.value += if (_description.value.isBlank()) text else " $text"
    }

    // für Preview
    fun checkIfImageReady(image: DreamImage?) {
        _isImageReady.value = image != null && !image.url
            .isNullOrBlank() && !image.interpretation.isNullOrBlank()
    }

    // Filter

    fun setSortArc(isAscending: Boolean) {
        _sortAsc.value = isAscending
    }

    fun toggleMoodFilter(mood: Mood) {
        val currentList = _selectedMoodsFilter.value.toMutableList()

        if (currentList.contains(mood)) {
            currentList.remove(mood)
        } else {
            currentList.add(mood)
        }

        _selectedMoodsFilter.value = currentList
    }

    fun toggleCategoryFilter(category: DreamCategory) {
        val currentList = _selectedCategoriesFilter.value.toMutableList()

        if (currentList.contains(category)) {
            currentList.remove(category)
        } else {
            currentList.add(category)
        }

        _selectedCategoriesFilter.value = currentList
    }

    fun toggleImageStylesFilter(style: ImageStyle) {
        val currentList = _selectedImageStylesFilter.value.toMutableList()

        if (currentList.contains(style)) {
            currentList.remove(style)
        } else {
            currentList.add(style)
        }

        _selectedImageStylesFilter.value = currentList
    }
}