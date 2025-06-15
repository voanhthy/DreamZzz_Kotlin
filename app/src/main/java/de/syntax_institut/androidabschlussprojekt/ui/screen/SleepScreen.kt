package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DismissButton
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamsSleepToggle
import de.syntax_institut.androidabschlussprojekt.ui.component.MonthDropdownMenu
import de.syntax_institut.androidabschlussprojekt.ui.component.MoodLineChart
import de.syntax_institut.androidabschlussprojekt.ui.component.SleepBoxButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SleepViewModel
import de.syntax_institut.androidabschlussprojekt.utils.helper.enableFullscreen
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth


// Sentinel-Wert für "Alle Monate".
// Jahr 0, Monat 1 ist ungültiges YearMonth-Datum, dient daher als spezieller Wert
val ALL_MONTHS_SENTINEL = YearMonth.of(0, 1)

@Composable
fun SleepScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToNightSky: ()-> Unit,
    onNavigateToInfoSleepPhase: () -> Unit,
    sleepViewModel: SleepViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val months by sleepViewModel.availableMonths.collectAsState()
    val selectedMonth by sleepViewModel.selectedMonth.collectAsState()
    val moodCounts by sleepViewModel.moodCountByMonth.collectAsState()
    val scrollState = rememberScrollState()

    val monthsWithAll = listOf(ALL_MONTHS_SENTINEL) + months

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as? Activity)?.enableFullscreen(true)
    }

    DisposableEffect(Unit) {
        onDispose {
            (context as? Activity)?.enableFullscreen(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.sleep),
            contentDescription = "Schlaf Screen",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                DismissButton(
                    onClickDismiss = { onNavigateToHome() }
                )
            }

            Text(
                stringResource(R.string.sleep),
                style = MaterialTheme.typography.titleLarge)

            Text("Ein erholsamer Schlaf ist wichtig. Hier beginnt deine nächtliche Reise – zu mehr Ruhe, besseren Träumen und einem klareren Kopf.",
                style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.padding(8.dp))

            Text("Deine Stimmungen nach dem Aufwachen im Überblick",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Mood Diagramm
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DreamZzzGray.copy(0.7f))
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MonthDropdownMenu(
                                months = months,
                                selected = selectedMonth,
                                onSelect = { month ->
                                    Log.d("SleepScreen", "Ausgewählter Monat: $month")
                                    sleepViewModel.setSelectedMonth(month)
                                }
                            )
                        }

                        MoodLineChart(
                            moodCounts = moodCounts,
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text("Wissenswertes",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

//            Spacer(modifier = Modifier.padding(4.dp))

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.sleep_phases_dreams),
                    subtitle = "Tauche ein in die Zyklen der Nacht.",
                    backgroundImageResId = R.drawable.background12,
                    onClick = { onNavigateToInfoSleepPhase() }
                )
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.sleep_disorders_problems),
                    subtitle = "Häufige Hürden für deinen Schlaf",
                    backgroundImageResId = R.drawable.background2,
                    onClick = {}
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.sleep_hygiene_rituals),
                    subtitle = "Rituale für erholsames Einschlafen",
                    backgroundImageResId = R.drawable.background9,
                    onClick = {}
                )
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.technology_sleep),
                    subtitle = "Digitale Helfer und Stolperfallen",
                    backgroundImageResId = R.drawable.background11,
                    onClick = {}
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.movement_sleep),
                    subtitle = "Wie Bewegung deine Nacht beeinflusst",
                    backgroundImageResId = R.drawable.background5,
                    onClick = {}
                )
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.mindfulness_meditation),
                    subtitle = "Ruhe finden vor dem Schlafengehen",
                    backgroundImageResId = R.drawable.background6,
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))
        }

//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 16.dp)
//        ) {
//            DreamsSleepToggle(
//                dreams = "",
//                sleep = "",
//                onSleepSelected = { onNavigateToNightSky }
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SleepScreenPreview() {
    // Use Theme here
    SleepScreen(
        onNavigateToHome = {},
        onNavigateToNightSky = {},
        onNavigateToInfoSleepPhase = {}
    )
}