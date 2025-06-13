package de.syntax_institut.androidabschlussprojekt.utils.helper

import androidx.compose.ui.graphics.Color
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorAnxious
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorDefault
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorGood
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorHappy
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorRelaxed
import de.syntax_institut.androidabschlussprojekt.ui.theme.MoodColorSad

object MoodColorHelper {
    fun getMoodColor(mood: Mood?): Color {
        return when (mood) {
            Mood.ANXIOUS -> MoodColorAnxious
            Mood.SAD -> MoodColorSad
            Mood.RELAXED -> MoodColorRelaxed
            Mood.GOOD -> MoodColorGood
            Mood.HAPPY -> MoodColorHappy
            else -> MoodColorDefault
        }
    }
}