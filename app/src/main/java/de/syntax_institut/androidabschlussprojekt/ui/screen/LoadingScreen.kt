package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


@Composable
fun LoadingScreen(
    onNavigateToPreview: (DreamImage) -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val dreamImage by dreamViewModel.dreamImage.collectAsState()

    LaunchedEffect(dreamImage) {
        when {
            dreamImage != null -> {
                Log.d("AddDreamScreen", "Bild wurde geladen - zu PreviewScreen navigieren")
                onNavigateToPreview(dreamImage!!)
                dreamViewModel.resetDreamImage()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ich bin ein LadeScreen")
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