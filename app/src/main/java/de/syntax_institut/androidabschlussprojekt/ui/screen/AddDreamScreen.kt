package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.R.attr.description
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.speech.RecognizerIntent
import android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import coil3.compose.AsyncImage
import coil3.util.CoilUtils.result
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzCalendar
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamCategoryPicker
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.component.ImageStylePicker
import de.syntax_institut.androidabschlussprojekt.ui.component.MoodPicker
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
    onClickNavigateToLoadingScreen: () -> Unit,
    onClickNavigateToPreviewScreen: (DreamImage) -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel(),
) {
    // State Variablen
    val title by dreamViewModel.title.collectAsState()
    val description by dreamViewModel.description.collectAsState()
    val dreamImage by dreamViewModel.dreamImage.collectAsState()
    val selectedCategory by dreamViewModel.selectedDreamCategory.collectAsState()
    val selectedMood by dreamViewModel.selectedMood.collectAsState()
    val selectedImageStyle by dreamViewModel.selectedImageStyle.collectAsState()
    val date by dreamViewModel.date.collectAsState()
    val result by dreamViewModel.analysisResult.collectAsState()
    val isLoading by dreamViewModel.isLoading.collectAsState()

    var showDatePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val spokenText = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()
        spokenText?.let {
            dreamViewModel.appendTranscribedDescription(it)
        }
    }

    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Sprich deinen Traum...")

        }
    }


//    LaunchedEffect(dreamImage) {
//        when {
//            dreamImage != null -> {
//                Log.d("AddDreamScreen", "Bild wurde geladen - zu PreviewScreen navigieren")
//                onClickNavigateToPreviewScreen(dreamImage!!)
//                dreamViewModel.resetDreamImage()
//            }
//        }
//    }

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
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {

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
                ),
                // Mikrofon-Icon für Sprachaufnahme
                trailingIcon = {
                    IconButton(
                        onClick = { launcher.launch(speechIntent) }
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = "Spracheingabe starten")
                    }
                }
            )

            // Datum auswählen
            val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)

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


//            OutlinedTextField(
//                value = formattedDate,
//                onValueChange = {},
//                readOnly = true,
//                label = { Text(stringResource(R.string.detail_date)) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable {
//                        showDatePicker = true
//                    }
//            )


            DreamZzzCalendar(
                selectedDate = date,
                onDateSelected = { dreamViewModel.updateDate(it) }
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

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                stringResource(R.string.choose_style).uppercase(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Style Picker
            ImageStylePicker(
                selectedStyle = selectedImageStyle,
                onClick = { dreamViewModel.updateImageStyle(it) }
            )

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

            result?.let {
                Text(it)
            }

            Spacer(modifier = Modifier.padding(16.dp))

            // Button zum Generieren
            DreamZzzTextButton(
                onClickText = {
                    dreamViewModel.fetchImage(description)
                    Log.d("ButtonTest", "Generate-Button wurde gedrückt")
                    dreamViewModel.analyzeImage(description)
                },
                title = stringResource(R.string.generate),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// englisch
@Preview(showBackground = true, locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddDreamScreenPreviewEN() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            onClickNavigateToLoadingScreen = {},
            onClickNavigateToPreviewScreen = {}
        )
    }
}

// deutsch
@Preview(showBackground = true, locale = "de")
@Composable
private fun AddDreamScreenPreview() {
    AndroidAbschlussprojektTheme {
        AddDreamScreen(
            onClickNavigateToLoadingScreen = {},
            onClickNavigateToPreviewScreen = {}
        )
    }
}