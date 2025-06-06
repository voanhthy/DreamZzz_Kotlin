package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.component.LoadingAnimation
import de.syntax_institut.androidabschlussprojekt.utils.getRandomSleepFact
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


@Composable
fun LoadingScreen(
    onNavigateToPreview: (DreamImage) -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val dreamImage by dreamViewModel.dreamImage.collectAsState()
    var sleepFact by remember { mutableStateOf(getRandomSleepFact()) }

    LaunchedEffect(dreamImage) {
        // erst navigieren, wenn Bild und Interpretation vorhanden sind
        if (dreamImage != null && !dreamImage!!.interpretation.isNullOrBlank()) {
            Log.d("AddDreamScreen", "Bild wurde geladen - zu PreviewScreen navigieren")
            delay(5000)
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
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(sleepFact,
            style = MaterialTheme.typography.bodyLarge)
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