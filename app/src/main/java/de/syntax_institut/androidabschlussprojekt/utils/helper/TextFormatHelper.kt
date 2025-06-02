package de.syntax_institut.androidabschlussprojekt.utils.helper

object TextFormatHelper {
    fun truncateText(text: String, max: Int): String {
        if (max <= 0) return text
        return if (text.length > max) text.take(max) + "..." else text
    }
}