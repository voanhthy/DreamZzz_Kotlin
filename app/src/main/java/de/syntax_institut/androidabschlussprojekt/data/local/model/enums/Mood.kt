package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

enum class Mood(val title: String) {
    HAPPY("Glücklich"),
    GOOD("Gut"),
    NEUTRAL("Neutral"),
    SAD("Traurig"),
    ANGRY("Wütend");

    // einheitliche, typsichere Liste für UI
    // alternativ: Mood.values().toList()
    companion object {
        val all = entries
    }
}
