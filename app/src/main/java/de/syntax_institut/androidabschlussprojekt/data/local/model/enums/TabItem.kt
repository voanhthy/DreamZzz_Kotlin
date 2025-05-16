package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

import androidx.annotation.StringRes
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.GalleryRoute
import de.syntax_institut.androidabschlussprojekt.ui.HomeRoute
import de.syntax_institut.androidabschlussprojekt.ui.SettingsRoute

enum class TabItem(val route: Any, val titleResId: Int) {
    HOME(HomeRoute, R.string.tab_home),
    GALLERY(GalleryRoute, R.string.tab_gallery),
    YOU(SettingsRoute, R.string.tab_you)
}