package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.component.PasswordTextField
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    onNavigateToRegisterScreen: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val emailInput by authViewModel.emailInput.collectAsState()
    val passwordInput by authViewModel.passwordInput.collectAsState()
    val showPasswordError by authViewModel.showPasswordError.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(16.dp))

        Text(stringResource(R.string.login),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp))

        OutlinedTextField(
            value = emailInput,
            onValueChange = { authViewModel.updateEmailInput(it) },
            label = { Text(stringResource(R.string.email),
                fontSize = 14.sp)},
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

        Spacer(modifier = Modifier.padding(2.dp))

        PasswordTextField(
            value = passwordInput,
            onValueChange = { authViewModel.updatePasswordInput(it) },
            label = stringResource(R.string.passwordInput),
            showAsError = showPasswordError,
            errorMessage = if (showPasswordError) stringResource(R.string.error_wrong_password) else null
        )

        Spacer(modifier = Modifier.padding(16.dp))

        DreamZzzTextButton(
            onClickText = {
                authViewModel.loginUser()
            },
            title = stringResource(R.string.login),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "Registriere dich hier"
            TextButton(
                onClick = {
                    onNavigateToRegisterScreen()
                }
            ) {
                Text(stringResource(R.string.no_account),
                    style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    // Use Theme here
    LoginScreen(
        onNavigateToRegisterScreen = {}
    )
}