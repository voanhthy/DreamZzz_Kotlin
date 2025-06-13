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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.LogoutButton
import de.syntax_institut.androidabschlussprojekt.ui.component.LogoutDialog
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel(),
    userProfileViewModel: UserProfileViewModel = koinViewModel()
) {
    val isNotificationOn by settingsViewModel.isNotificationOnStateFlow.collectAsState()
    val isDarkModeOn by settingsViewModel.isDarkModeStateFlow.collectAsState()

    val email by userProfileViewModel.emailFromAuth.collectAsState()
    val memberSince by userProfileViewModel.memberSinceFromAuth.collectAsState()
    val userProfile by userProfileViewModel.userProfile.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
        ) {
            // Logout Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                LogoutButton(
                    onClickLogout = {
                        showDialog = true
                    }
                )
            }

            Text(
                stringResource(R.string.tab_you).uppercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            HorizontalDivider()

            // Name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.name),
                    style = MaterialTheme.typography.bodyLarge
                )

                val fullName =
                    if (userProfile?.firstName?.isNotBlank() == true || userProfile?.lastName?.isNotBlank() == true) {
                        "${userProfile?.firstName.orEmpty()} ${userProfile?.lastName.orEmpty()}"
                    } else {
                        stringResource(R.string.guest)
                    }
                Text(
                    fullName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()

            // E-Mail
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.email),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    email ?: "keine E-Mail",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()


            // Mitglied seit
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.member_since),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(memberSince?.let { SimpleDateFormat("dd.MM.yyyy").format(it) }
                    ?: "Nicht verfügbar",
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
                Text(
                    if (isNotificationOn) stringResource(R.string.on) else stringResource(R.string.off),
                    modifier = Modifier.clickable {
                        settingsViewModel.toggleNotificationOn()
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()


            // Dunkelmodus
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.dark_mode),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    if (isDarkModeOn) stringResource(R.string.on) else stringResource(R.string.off),
                    modifier = Modifier.clickable {
                        settingsViewModel.toggleDarkMode()
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()

            // Sprache
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.language),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Deutsch",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()
        }

//        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.padding(32.dp))

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
        
        if (showDialog) {
            LogoutDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    authViewModel.logoutUser()
                    showDialog = false },
                showDialog = showDialog
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    // Use Theme here
    SettingsScreen()
}