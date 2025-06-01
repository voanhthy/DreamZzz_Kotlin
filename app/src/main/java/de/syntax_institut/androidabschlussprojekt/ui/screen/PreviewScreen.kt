package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.PreviewViewModel

@Composable
fun PreviewScreen(
    dreamViewModel: DreamViewModel = koinViewModel(),
    previewViewModel: PreviewViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val dreamImage by previewViewModel.dreamStateFlow.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dreamImage?.url?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = "Bild",
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Speichern Button
        DreamZzzTextButton(
            onClickText = {
                dreamViewModel.saveImage()
            },
            title = stringResource(R.string.save),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenPreview() {
    // Use Theme here
    PreviewScreen()
}