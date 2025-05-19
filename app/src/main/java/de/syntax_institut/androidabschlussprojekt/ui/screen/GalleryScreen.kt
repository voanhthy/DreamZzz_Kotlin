package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    val dreamImages by dreamViewModel.savedDreamImages.collectAsState(listOf())

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(dreamImages) { dream ->
            AsyncImage(
                model = dream.url,
                contentDescription = "generiertes Bild"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {
    // Use Theme here
    GalleryScreen()
}