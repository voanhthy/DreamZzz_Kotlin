package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamDetailViewModel


@Composable
fun DreamDetailScreen(
    modifier: Modifier = Modifier,
    dreamDetailViewModel: DreamDetailViewModel = koinViewModel()
) {
    val dreamImage by dreamDetailViewModel.dreamStateFlow.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // generiertes Bild
        item {
            dreamImage?.let { image ->
                AsyncImage(
                    model = image.url,
                    contentDescription = "Bild"
                )
            }
        }

        // Box mit Infos
        item {
            dreamImage?.let { image ->
                Box(
                    modifier = Modifier
                ) {
                    Text(image.prompt)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DreamDetailScreenPreview() {
    // Use Theme here
    DreamDetailScreen()
}