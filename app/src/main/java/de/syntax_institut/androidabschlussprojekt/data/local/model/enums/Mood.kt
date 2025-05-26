package de.syntax_institut.androidabschlussprojekt.data.local.model.enums
import de.syntax_institut.androidabschlussprojekt.R

enum class Mood(val titleResId: Int) {
    HAPPY(R.string.mood_happy),
    GOOD(R.string.mood_good),
    NEUTRAL(R.string.mood_neutral),
    SAD(R.string.mood_sad),
    ANGRY(R.string.mood_angry)
}
