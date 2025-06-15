package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.component.LoadingAnimation
import de.syntax_institut.androidabschlussprojekt.ui.component.LoadingSpinner
import de.syntax_institut.androidabschlussprojekt.utils.getRandomSleepFact
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.math.tan


@Composable
fun LoadingScreen(
    onNavigateToPreview: (DreamImage) -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val dreamImage by dreamViewModel.dreamImage.collectAsState()
    var sleepFact by remember { mutableStateOf(getRandomSleepFact()) }
    val isImageReady by dreamViewModel.isImageReady.collectAsState()
    val isLoading by dreamViewModel.isLoading.collectAsState()

    LaunchedEffect(dreamImage) {
        dreamViewModel.checkIfImageReady(dreamImage)
    }

    LaunchedEffect(isImageReady, isLoading) {
        if (!isLoading && isImageReady && dreamImage != null) {
            Log.d("LoadingScreen", "Bild bereit - zu PreviewScreen navigieren")
            delay(1000)
            onNavigateToPreview(dreamImage!!)
        }
    }

    // alle 4 Sekunden neuen sleep fact
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            sleepFact = getRandomSleepFact()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingAnimation(
            modifier = Modifier
                .size(100.dp)
        )
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(160.dp)
//        ) {
//            LoadingSpinner()
//        }

        Spacer(modifier = Modifier.padding(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Crossfade(
                targetState = sleepFact,
                label = "SleepFactCrossFade"
            ) { fact ->
                Text(
                    fact,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    // Use Theme here
    LoadingScreen(
        onNavigateToPreview = {}
    )
}