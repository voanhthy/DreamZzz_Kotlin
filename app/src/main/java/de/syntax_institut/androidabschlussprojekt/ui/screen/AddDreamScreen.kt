package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.TextButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import androidx.compose.runtime.getValue



@Composable
fun AddDreamScreen(
    text: String,
    onInputChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    dreamViewModel: DreamViewModel = viewModel()
) {
    val dreamImage by dreamViewModel.dreamImage.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.add_new_dream).uppercase(),
            fontSize = 30.sp)


        // Texteingabefeld: Titel
        OutlinedTextField(
            value = text,
            onValueChange = onInputChange,
            label = {
                Text(stringResource(R.string.title))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Texteingabefeld: Traumbeschreibung
        OutlinedTextField(
            value = text,
            onValueChange = onInputChange,
            label = {
                Text(stringResource(R.string.tell_about_dream))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        // Datum auswählen


        // Traum-Kategorie auswählen


        // Style Picker


        // Mood Picker

        // TODO: temporär Bild anzeigen
        dreamImage?.url?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Bild",
                modifier = Modifier.fillMaxWidth()
            )
        }


        // Button zum Generieren
        TextButton(
            onClickText = {
                dreamViewModel.fetchImage(text)
            },
            title = stringResource(R.string.generate)
        )
    }
}

// englisch
@Preview(showBackground = true, locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddDreamScreenPreviewEN() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            text = "",
            onInputChange = {}
        )
    }
}

// deutsch
@Preview(showBackground = true, locale = "de")
@Composable
private fun AddDreamScreenPreview() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            text = "",
            onInputChange = {}
        )
    }
}