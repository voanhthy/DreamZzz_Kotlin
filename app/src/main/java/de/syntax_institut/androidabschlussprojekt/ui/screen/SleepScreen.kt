package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DismissButton
import de.syntax_institut.androidabschlussprojekt.ui.component.MoodLineChart
import de.syntax_institut.androidabschlussprojekt.ui.component.SleepBoxButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import de.syntax_institut.androidabschlussprojekt.utils.helper.enableFullscreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun SleepScreen(
    onNavigateToHome: () -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val moodCounts by dreamViewModel.moodCount.collectAsState()
    val scrollState = rememberScrollState()

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                DismissButton(
                    onClickDismiss = { onNavigateToHome() }
                )
            }
            
            Text("Schlaf",
                style = MaterialTheme.typography.titleLarge)

            Text("Ein erholsamer Schlaf ist wichtig. Hier beginnt deine nächtliche Reise – zu mehr Ruhe, besseren Träumen und einem klareren Kopf.",
                style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.padding(8.dp))

            Text("Deine Stimmungen nach dem Aufwachen im Überblick",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Mood Diagramm
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DreamZzzGray.copy(0.7f))
            ) {
                MoodLineChart(
                    moodCounts = moodCounts,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                )
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
                    title = "Besserer Schlaf"
                )
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = "Stressabbau"
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = "Meditation"
                )
                SleepBoxButton(
                    modifier = Modifier.weight(1f),
                    title = "Ernährung & Schlaf"
                )
            }

            SleepBoxButton(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "FAQ"
            )

            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SleepScreenPreview() {
    // Use Theme here
    SleepScreen(
        onNavigateToHome = {}
    )
}