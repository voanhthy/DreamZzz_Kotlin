package de.syntax_institut.androidabschlussprojekt.data.local.model.enums
import androidx.compose.ui.graphics.Color
import de.syntax_institut.androidabschlussprojekt.R

enum class DreamCategory(val titleResId: Int) {
    NORMAL(R.string.category_normal),
    NIGHTMARE(R.string.category_nightmare),
    LUCID(R.string.category_lucid)
}

fun getEmojiForCategory(category: DreamCategory): String {
    return when (category) {
        DreamCategory.NORMAL -> "⭐"
        DreamCategory.NIGHTMARE -> "\uD83D\uDCAB"
        DreamCategory.LUCID -> "✨"
    }
}

fun getColorForCategory(category: DreamCategory): Color {
    return when (category) {
        DreamCategory.NORMAL -> Color.Yellow
        DreamCategory.NIGHTMARE -> Color.Red
        DreamCategory.LUCID -> Color.Yellow
    }
}