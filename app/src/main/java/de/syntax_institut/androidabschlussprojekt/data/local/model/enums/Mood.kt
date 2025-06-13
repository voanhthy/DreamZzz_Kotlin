package de.syntax_institut.androidabschlussprojekt.data.local.model.enums
import de.syntax_institut.androidabschlussprojekt.R

enum class Mood(val titleResId: Int, val value: Int) {
    HAPPY(R.string.mood_happy, 5),
    GOOD(R.string.mood_good, 4),
    RELAXED(R.string.mood_relaxed, 3),
    SAD(R.string.mood_sad, 2),
    ANXIOUS(R.string.mood_anxious, 1)
}

