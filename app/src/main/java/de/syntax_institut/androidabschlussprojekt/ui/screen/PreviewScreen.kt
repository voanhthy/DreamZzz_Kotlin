package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzTextButton
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.component.SuccessDialog
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.PreviewViewModel

@Composable
fun PreviewScreen(
    dreamViewModel: DreamViewModel = koinViewModel(),
    onNavigateToDreamDetail: (DreamImage) -> Unit,
    modifier: Modifier = Modifier
) {
    val dreamImage by dreamViewModel.dreamImage.collectAsState()
    val isLoading by dreamViewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Log.d("PreviewScreen", "Bild ist da: ${dreamImage?.url}")

            dreamImage?.url?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Bild",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.padding(16.dp))

            // Speichern Button - nur wenn Bild geladen
            DreamZzzTextButton(
                onClickText = {
                    dreamImage?.let {
                        dreamViewModel.saveImage()
                        showDialog = true
                    }
                },
                title = stringResource(R.string.save),
                modifier = Modifier.fillMaxWidth()
            )

            if (showDialog) {
                SuccessDialog(
                    showDialog = true,
                    onDismiss = {
                        showDialog = false
                        dreamImage?.let {
                            onNavigateToDreamDetail(it)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenPreview() {
    // Use Theme here
    PreviewScreen(
        onNavigateToDreamDetail = {}
    )
}