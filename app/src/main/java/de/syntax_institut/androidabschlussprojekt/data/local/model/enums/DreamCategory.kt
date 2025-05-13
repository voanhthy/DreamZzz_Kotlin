package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

enum class DreamCategory(val title: String) {
    NORMAL("Normaler Traum"),
    NIGHTMARE("Albtraum"),
    LUCID("Luzider Traum");

    // einheitliche, typsichere Liste für UI
    companion object {
        val all = DreamCategory.entries
    }
}