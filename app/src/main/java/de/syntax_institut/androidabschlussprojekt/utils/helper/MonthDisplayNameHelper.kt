package de.syntax_institut.androidabschlussprojekt.utils.helper

import java.time.YearMonth
import java.util.Locale

// Sentinel-Wert für "Alle Monate".
// Jahr 0, Monat 1 ist ungültiges YearMonth-Datum, dient daher als spezieller Wert
val ALL_MONTHS_SENTINEL = YearMonth.of(0, 1)

object MonthDisplayNameHelper {
    // Anzeigenamen für einen Monat erhalten
    fun getMonthDisplayName(month: YearMonth): String {
        return if (month == ALL_MONTHS_SENTINEL) {
            "Alle Monate"   // Spezialbehandlung für den Sentinel-Wert
        } else {
            month.month
                .getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
                .replaceFirstChar { it.uppercase() } + " ${month.year}"
        }
    }
}