package de.syntax_institut.androidabschlussprojekt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDao
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.ui.screen.ALL_MONTHS_SENTINEL
import de.syntax_institut.androidabschlussprojekt.utils.helper.DateUtils.toLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth


class SleepViewModel(
    private val dreamImagedao: DreamImageDao
): ViewModel() {

    val savedDreamImages: Flow<List<DreamImage>> = dreamImagedao.getAllItems()

    // Monat für Mood Diagramm
    private val _selectedMonth = MutableStateFlow(YearMonth.now())
    val selectedMonth = _selectedMonth.asStateFlow()

    // alle Monate für Mood Diagramm
    val moodCount: StateFlow<Map<Int, Int>> = savedDreamImages
        .map { dreams ->
            Mood.values().associate { mood ->
                mood.value to dreams.count { it.mood == mood }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )

    //
    val moodCountByMonth: StateFlow<Map<Int, Int>> = combine(savedDreamImages, selectedMonth) { dreams, month ->
        val filtered = if (month == ALL_MONTHS_SENTINEL) {
            dreams
        } else {
            dreams.filter { it.date.toLocalDate().year == month.year && it.date.toLocalDate().month == month.month }
        }

        Mood.values().associate { mood ->
            mood.value to filtered.count { it.mood == mood }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyMap()
    )

    // sortierte List von YearMonth
    val availableMonths: StateFlow<List<YearMonth>> = savedDreamImages
        .map { dreams ->
            val months = dreams
                // jedes DreamImage -> YearMonth
                .map { dream ->
                    dream.date.toLocalDate().let { local ->
                        YearMonth.of(local.year, local.month) } }
                .distinct()              // nur eindeutige Monate
                .sortedDescending()      // absteigend sortieren
            listOf(ALL_MONTHS_SENTINEL) + months    // "Alle Monate" an den Anfang
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf(ALL_MONTHS_SENTINEL)
        )

    init {
        viewModelScope.launch {
            val generatedMonths = generatePastMonths(12)
        }
    }

    // bewegt Monat um +/- Monate
    fun changeMonth(offset: Long) {
        _selectedMonth.value = _selectedMonth.value.plusMonths(offset)
    }

    fun setSelectedMonth(month: YearMonth) {
        _selectedMonth.value = month
    }

    // zur Generierung der letzten Monate (Beispiel)
    private fun generatePastMonths(count: Int): List<YearMonth> {
        val months = mutableListOf<YearMonth>()
        var current = YearMonth.now()
        repeat(count) {
            months.add(current)
            current = current.minusMonths(1)
        }
        return months
    }
}