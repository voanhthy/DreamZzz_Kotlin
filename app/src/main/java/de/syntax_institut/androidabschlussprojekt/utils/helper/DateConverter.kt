package de.syntax_institut.androidabschlussprojekt.utils.helper

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    // Long-Wert in Date-Objekt zurückwandeln
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    // Date-Objekt in einen Long-Wert umwandeln
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}