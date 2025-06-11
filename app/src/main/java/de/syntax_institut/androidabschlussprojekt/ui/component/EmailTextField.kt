package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun EmailTextField(
    emailInput: String,
    onEmailTextChange: (String) -> Unit,
    showSupportingText: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = emailInput,
        onValueChange = onEmailTextChange,
        label = {
            Text(
                stringResource(R.string.email),
                fontSize = 14.sp
            )
        },
        modifier = modifier
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,         // nächstes Eingabefeld
            keyboardType = KeyboardType.Email
        ),
        supportingText = { if (showSupportingText) Text(stringResource(R.string.email_hint)) },
        isError = showSupportingText
    )
}

@Preview(showBackground = true)
@Composable
private fun EmailTextFieldPreview() {
    // Use Theme here
    EmailTextField(
        emailInput = "Email",
        onEmailTextChange = {},
        showSupportingText = true
    )
}