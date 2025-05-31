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
import de.syntax_institut.androidabschlussprojekt.ui.component.LogoutButton
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val isDarkModeOn by settingsViewModel.isDarkModeStateFlow.collectAsState()
    val firstName by authViewModel.firstNameInput.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Logout Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            LogoutButton(
                onClickLogout = {
                    authViewModel.logoutUser()
                }
            )
        }

        Text(stringResource(R.string.tab_you).uppercase(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
            )

        HorizontalDivider()

        // Name
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.name),
                style = MaterialTheme.typography.bodyLarge)
            Text(firstName,
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()

        // E-Mail
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.email),
                style = MaterialTheme.typography.bodyLarge)
            Text("keine E-Mail",
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()


        // Mitglied seit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.member_since),
                style = MaterialTheme.typography.bodyLarge)
            Text("hier anmelden",
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()


        // Benachrichtigungen
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.notifications))
            Text(if (isNotificationOn) "an" else "aus",
                modifier = Modifier.clickable {
                    settingsViewModel.toggleNotificationOn()
                },
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()


        // Dunkelmodus
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.dark_mode),
                style = MaterialTheme.typography.bodyLarge)
            Text(if (isDarkModeOn) "an" else "aus",
                modifier = Modifier.clickable {
                    settingsViewModel.toggleDarkMode()
                },
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()

        // Sprache
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.language),
                style = MaterialTheme.typography.bodyLarge)
            Text("Deutsch",
                style = MaterialTheme.typography.bodyLarge)
        }
        HorizontalDivider()

        Spacer(modifier = Modifier.weight(1f))

        Column {
            HorizontalDivider()
            // Über diese App
            Text(stringResource(R.string.about_this_app),
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider()

            // Feedback
            Text(stringResource(R.string.feedback),
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider()

            // FAQ
            Text(stringResource(R.string.faq),
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge)
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