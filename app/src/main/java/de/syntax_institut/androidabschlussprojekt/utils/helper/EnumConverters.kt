package de.syntax_institut.androidabschlussprojekt.utils.helper

import androidx.room.TypeConverter
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood

class EnumConverters {

    @TypeConverter
    fun fromMoodList(moods: List<Mood?>): String? {
        return moods.joinToString(",") { it!!.name }     // Mood1,Mood2...
    }

    @TypeConverter
    fun toMoodList(data: String?): List<Mood?> {
        return data?.split(",")!!.mapNotNull { moodName ->
            Mood.entries.find { it.name == moodName }
        }

        }
    }

    @TypeConverter
    fun fromCategoryList(categories: List<DreamCategory?>): String? {
        return categories.joinToString(",") { it!!.name }
    }

    @TypeConverter
    fun toCategoryList(data: String?): List<DreamCategory?> {
        return data?.split(",")!!.mapNotNull { categoryName ->
            DreamCategory.entries.find { it.name == categoryName }
        }

    @TypeConverter
    fun fromStyleList(styles: List<ImageStyle?>): String? {
        return styles.joinToString(",") { it!!.name }
    }

    @TypeConverter
    fun toStyleList(data: String?): List<ImageStyle?> {
        return data?.split(",")!!.mapNotNull { styleName ->
            ImageStyle.entries.find { it.name == styleName }
        }
    }
}