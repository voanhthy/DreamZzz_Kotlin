package de.syntax_institut.androidabschlussprojekt.utils.helper

import androidx.room.TypeConverter
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood

class EnumConversters {

    @TypeConverter
    fun fromMood(mood: Mood?): String? = mood?.name

    @TypeConverter
    fun toMood(value: String?): Mood? = value?.let { Mood.valueOf(it) }

    @TypeConverter
    fun fromCategory(category: DreamCategory?): String? = category?.name

    @TypeConverter
    fun toCategory(value: String?): DreamCategory? = value?.let { DreamCategory.valueOf(it) }

    @TypeConverter
    fun fromStyle(style: ImageStyle?): String? = style?.name

    @TypeConverter
    fun toStyle(value: String?): ImageStyle? = value?.let { ImageStyle.valueOf(it) }
}