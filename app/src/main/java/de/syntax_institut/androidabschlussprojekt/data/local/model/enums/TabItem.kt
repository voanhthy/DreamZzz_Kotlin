package de.syntax_institut.androidabschlussprojekt.data.local.model.enums

import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.navigation.GalleryRoute
import de.syntax_institut.androidabschlussprojekt.navigation.HomeRoute
import de.syntax_institut.androidabschlussprojekt.navigation.SettingsRoute

enum class TabItem(val route: Any, val titleResId: Int) {
    HOME(HomeRoute, R.string.tab_home),
    GALLERY(GalleryRoute, R.string.tab_gallery),
    YOU(SettingsRoute, R.string.tab_you)
}