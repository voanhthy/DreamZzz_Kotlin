package de.syntax_institut.androidabschlussprojekt.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import de.syntax_institut.androidabschlussprojekt.navigation.AppStart
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme


@Composable
fun DreamZzzRoot(
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val isDarkMode by settingsViewModel.isDarkModeStateFlow.collectAsState()

    AndroidAbschlussprojektTheme(
        darkTheme = isDarkMode
    ) {
        AppStart()
    }
}

@Preview(showBackground = true)
@Composable
private fun DreamZzzRootPreview() {
    // Use Theme here
    DreamZzzRoot()
}