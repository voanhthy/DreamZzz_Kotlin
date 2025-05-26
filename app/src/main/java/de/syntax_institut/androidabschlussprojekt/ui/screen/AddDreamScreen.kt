package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamCategoryPicker
import de.syntax_institut.androidabschlussprojekt.ui.component.MoodPicker
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("RememberReturnType")
@Composable
fun AddDreamScreen(
    modifier: Modifier = Modifier,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    // State Variablen
    val title by dreamViewModel.title.collectAsState()
    val description by dreamViewModel.description.collectAsState()
    val dreamImage by dreamViewModel.dreamImage.collectAsState()
    val selectedCategory by dreamViewModel.selectedDreamCategory.collectAsState()
    val selectedMood by dreamViewModel.selectedMood.collectAsState()
    val date by dreamViewModel.date.collectAsState()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            stringResource(R.string.add_new_dream).uppercase(),
            fontSize = 30.sp
        )


        // Texteingabefeld: Titel
        OutlinedTextField(
            value = title,
            onValueChange = { dreamViewModel.updateTitle(it) },
            label = {
                Text(stringResource(R.string.title))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Texteingabefeld: Traumbeschreibung
        OutlinedTextField(
            value = description,
            onValueChange = { dreamViewModel.updateDescription(it) },
            label = {
                Text(stringResource(R.string.tell_about_dream))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        // Datum auswählen
        val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)

        if (showDatePicker) {
            // Kalender Instanz basierend auf dem aktuellem Datum
            LaunchedEffect(showDatePicker) {
                val calendar = Calendar.getInstance().apply { time = date }

                val dialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->

                        val newDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time
                        dreamViewModel.updateDate(newDate)
                        showDatePicker = false
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.setOnDismissListener {
                    showDatePicker = false
                }
                dialog.show()
            }
        }

        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.detail_date)) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePicker = true
                }
        )

//        if (showDatePicker) {
//            DisposableEffect(Unit) {
//                datePickerDialog.show()
//                onDispose {  }
//            }
//        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Traum-Kategorie auswählen
        DreamCategoryPicker(
            selectedCategory = selectedCategory,
            onClickSelected = { dreamViewModel.updateDreamCategory(it) }
        )

        // Style Picker


        Spacer(modifier = Modifier.padding(16.dp))

        // Mood Picker TODO: in eine Box packen
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            MoodPicker(
                selectedMood = selectedMood,
                onSelectedMood = { dreamViewModel.setMood(it) }
            )
        }

        // TODO: temporär Bild anzeigen
        dreamImage?.url?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Bild",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        // Button zum Generieren
        DreamZzzTextButton(
            onClickText = {
                dreamViewModel.fetchImage(description)
                Log.d("ButtonTest", "Generate-Button wurde gedrückt")
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
        AddDreamScreen()
    }
}

// deutsch
@Preview(showBackground = true, locale = "de")
@Composable
private fun AddDreamScreenPreview() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen()
    }
}