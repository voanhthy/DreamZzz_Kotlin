package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamCategoryPicker
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzCalendar
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.component.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.ui.component.ImageStylePicker
import de.syntax_institut.androidabschlussprojekt.ui.component.MoodPicker
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.Locale


@SuppressLint("RememberReturnType")
@Composable
fun AddDreamScreen(
    modifier: Modifier = Modifier,
    onClickNavigateToLoadingScreen: () -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    // State Variablen
    val title by dreamViewModel.title.collectAsState()
    val description by dreamViewModel.description.collectAsState()
    val selectedCategory by dreamViewModel.selectedDreamCategory.collectAsState()
    val selectedMood by dreamViewModel.selectedMood.collectAsState()
    val selectedImageStyle by dreamViewModel.selectedImageStyle.collectAsState()
    val date by dreamViewModel.date.collectAsState()
    val isLoading by dreamViewModel.isLoading.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    // Speech-to-Text
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(           // Aktivität starten
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val spokenText = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
        spokenText?.let {
            dreamViewModel.appendTranscribedDescription(it)
        }
    }

    // Intent startet Spracherkennungsfunktion
    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Sprich deinen Traum...")

        }
    }


    LaunchedEffect(isLoading) {
        if (isLoading) {
            Log.d("AddDreamScreen", "Bild wird geladen")
            onClickNavigateToLoadingScreen()
        } else {
            Log.d("AddDreamScreen", "Kein Bild, kein Ladevorgang")
        }
    }


    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(0.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.padding(16.dp))

                Text(
                    stringResource(R.string.add_new_dream),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 34.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Texteingabefeld: Titel
                OutlinedTextField(
                    value = title,
                    onValueChange = { dreamViewModel.updateTitle(it) },
                    label = {
                        Text(stringResource(R.string.title))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    // Texteingabefeld: Traumbeschreibung
                    OutlinedTextField(
                        value = description,
                        onValueChange = { dreamViewModel.updateDescription(it) },
                        label = {
                            Text(stringResource(R.string.tell_about_dream))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    // Mikrofon-Icon für Sprachaufnahme
                    IconButton(
                        onClick = { launcher.launch(speechIntent) },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = "Spracheingabe starten"
                        )
                    }
                }


                // Datum auswählen

                if (showDatePicker) {
                    // Kalender Instanz basierend auf dem aktuellem Datum

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

                Spacer(modifier = Modifier.padding(16.dp))

                DreamZzzCalendar(
                    selectedDate = date,
                    onDateSelected = { dreamViewModel.updateDate(it) }
                )

                Text(stringResource(R.string.type_of_dream_add_screen).uppercase(),
                    modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            // Traum-Kategorie auswählen
            DreamCategoryPicker(
                selectedCategory = selectedCategory,
                onClickSelected = { dreamViewModel.updateDreamCategory(it) }
            )
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    stringResource(R.string.choose_style).uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        item {
            // Style Picker
            ImageStylePicker(
                selectedStyle = selectedImageStyle,
                onClick = { dreamViewModel.updateImageStyle(it) }
            )
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.padding(8.dp))

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

                Spacer(modifier = Modifier.padding(16.dp))

                // Button zum Generieren
                DreamZzzTextButton(
                    onClickText = {
                        if (description.isEmpty()) {
                            showErrorDialog = true
                        } else {
                            dreamViewModel.fetchImage(description)
                            Log.d("ButtonTest", "Generate-Button wurde gedrückt")
                        }
                    },
                    title = stringResource(R.string.generate),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    ErrorDialog(
        showDialog = showErrorDialog,
        onDismiss = { showErrorDialog = false }
    )
}


// englisch
@Preview(showBackground = true, locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddDreamScreenPreviewEN() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            onClickNavigateToLoadingScreen = {}
        )
    }
}

// deutsch
@Preview(showBackground = true, locale = "de")
@Composable
private fun AddDreamScreenPreview() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            onClickNavigateToLoadingScreen = {}
        )
    }
}