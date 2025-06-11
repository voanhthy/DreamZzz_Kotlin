package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.component.PasswordTextField
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    onNavigateToLoginScreen: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val firstNameInput by authViewModel.firstNameInput.collectAsState()
    val lastNameInput by authViewModel.lastNameInput.collectAsState()
    val emailInput by authViewModel.emailInput.collectAsState()
    val passwordInput by authViewModel.passwordInput.collectAsState()
    val passwordRepeatInput by authViewModel.passwordRepeatInput.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.register),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Vorname
            OutlinedTextField(
                value = firstNameInput,
                onValueChange = { authViewModel.updateFirstNameInput(it) },
                label = { Text(stringResource(R.string.first_name)) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.padding(8.dp))

            // Nachname
            OutlinedTextField(
                value = lastNameInput,
                onValueChange = { authViewModel.updateLastNameInput(it) },
                label = { Text(stringResource(R.string.last_name)) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        // Email
        OutlinedTextField(
            value = emailInput,
            onValueChange = { authViewModel.updateEmailInput(it) },
            label = {
                Text(
                    stringResource(R.string.email),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
//                .height(40.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

//        Spacer(modifier = Modifier.padding(2.dp))

        // Password
//        OutlinedTextField(
//            value = passwordInput,
//            onValueChange = { authViewModel.updatePasswordInput(it) },
//            label = { Text(stringResource(R.string.passwordInput)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White,
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent
//            )
//        )
        PasswordTextField(
            value = passwordInput,
            onValueChange = { authViewModel.updatePasswordInput(it) },
            label = stringResource(R.string.passwordInput)
        )

        // Password wiederholen
//        OutlinedTextField(
//            value = passwordRepeatInput,
//            onValueChange = { authViewModel.updatePasswordRepeatInput(it) },
//            label = { Text(stringResource(R.string.passwordInput)) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White,
//                focusedTextColor = Color.Black,
//                unfocusedTextColor = Color.Black,
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent
//            )
//        )
        PasswordTextField(
            value = passwordRepeatInput,
            onValueChange = { authViewModel.updatePasswordRepeatInput(it) },
            label = stringResource(R.string.passwordInput)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        DreamZzzTextButton(
            onClickText = {
                authViewModel.registerUser()
            },
            title = stringResource(R.string.register),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // zu RegisterScreen navigieren
            TextButton(
                onClick = {
                    onNavigateToLoginScreen()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.already_have_an_account),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    // Use Theme here
    RegisterScreen(
        onNavigateToLoginScreen = {}
    )
}