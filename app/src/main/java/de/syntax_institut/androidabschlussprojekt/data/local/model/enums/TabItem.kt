package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

import androidx.annotation.StringRes
import de.syntax_institut.androidabschlussprojekt.R

enum class TabItem(@StringRes val titleResId: Int) {        // Ressourcen-ID (zeigt auf Text)
    HOME(R.string.tab_home),
    GALLERY(R.string.tab_gallery),
    YOU(R.string.tab_you)
}