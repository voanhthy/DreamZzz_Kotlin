package de.syntax_institut.androidabschlussprojekt.data.local.model.enums
import de.syntax_institut.androidabschlussprojekt.R

enum class DreamCategory(val titleResId: Int) {
    NORMAL(R.string.category_normal),
    NIGHTMARE(R.string.category_nightmare),
    LUCID(R.string.category_lucid)
}