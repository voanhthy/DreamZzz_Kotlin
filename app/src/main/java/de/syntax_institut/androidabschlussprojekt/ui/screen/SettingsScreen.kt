package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val isDarkModeOn by settingsViewModel.isDarkModeStateFlow.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Logout Button


        Text(stringResource(R.string.tab_you).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
            )

        HorizontalDivider()

//        Settings.entries.forEach { label ->
//            Row {
//                Text(stringResource(label.labelResId))
//            }
//            HorizontalDivider()
//        }

        // Name
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.name))
            Text("Gast")
        }
        HorizontalDivider()

        // E-Mail
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.email))
            Text("keine E-Mail")
        }
        HorizontalDivider()


        // Mitglied seit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.member_since))
            Text("hier anmelden")
        }
        HorizontalDivider()


        // Benachrichtigungen
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.notifications))
            Text(if (isNotificationOn) "an" else "aus",
                modifier = Modifier.clickable {
                    settingsViewModel.toggleNotificationOn()
                })
        }
        HorizontalDivider()


        // Dunkelmodus
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.dark_mode))
            Text(if (isDarkModeOn) "an" else "aus",
                modifier = Modifier.clickable {
                    settingsViewModel.toggleDarkMode()
                })
        }
        HorizontalDivider()

        // Sprache
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.language))
            Text("Deutsch")
        }
        HorizontalDivider()

        Spacer(modifier = Modifier.weight(1f))

        Column {
            HorizontalDivider()
            // Über diese App
            Text(stringResource(R.string.about_this_app))
            HorizontalDivider()

            // Feedback
            Text(stringResource(R.string.feedback))
            HorizontalDivider()

            // FAQ
            Text(stringResource(R.string.faq))
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