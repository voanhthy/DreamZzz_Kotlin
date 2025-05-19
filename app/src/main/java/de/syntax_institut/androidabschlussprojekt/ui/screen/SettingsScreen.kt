package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Settings
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val isDarkModeOn by settingsViewModel.isDarkModeStateFlow.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.tab_you))

        HorizontalDivider()


        Settings.entries.forEach { label ->
            Row {
                Text(stringResource(label.labelResId))
            }
            HorizontalDivider()
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    // Use Theme here
    SettingsScreen()
}