package de.syntax_institut.androidabschlussprojekt.utils.helper

import android.icu.util.Calendar
import java.util.Date

object DateUtils {
    // Datum ohne Uhrzeit anzeigen (Date)
    fun dateWithoutTimestamp(date: Date): Date {
        val calendar = Calendar.getInstance().apply { time = date }

        // für den Vergleich Std, Min, Sek, Millisek auf 0 setzen
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return  calendar.time
    }

    // Datum ohne Uhrzeit anzeigen (Long)
    fun dateWithoutTimestampLong(date: Date): Long {
        val calendar = Calendar.getInstance().apply { time = date }

        // für den Vergleich Std, Min, Sek, Millisek auf 0 setzen
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return  calendar.timeInMillis
    }
}
