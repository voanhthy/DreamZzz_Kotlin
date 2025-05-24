package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.AddButton
import de.syntax_institut.androidabschlussprojekt.ui.component.GalleryGrid
import de.syntax_institut.androidabschlussprojekt.ui.component.GalleryListItem


@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetailScreen: (DreamImage) -> Unit,
    onNavigateToAddDreamScreen: () -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    var isGrid by remember { mutableStateOf(false) }
    val dreamImages by dreamViewModel.savedDreamImages.collectAsState(listOf())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Toggle List/Grid
            Button(
                onClick = { isGrid = !isGrid },
                shape = CircleShape,
                modifier = Modifier.size(36.dp),
                contentPadding = PaddingValues(0.dp),      // entfernt Standardabstände
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = null,
                )
            }

            // Add Button
            AddButton(
                onAddClick = onNavigateToAddDreamScreen
            )
        }
        Text(stringResource(R.string.tab_gallery))
        Text(stringResource(R.string.gallery_description))

        // Button minus plus
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.button_minus))
            }
            TextButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.button_plus))
            }
        }

        if (isGrid) {
            // GalleryGrid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(dreamImages) { index, dream ->
                    AsyncImage(
                        model = dream.url,
                        contentDescription = "Traumbild",
                        modifier = Modifier
                            .clickable {
                            onNavigateToDetailScreen(dream)
                        }
                    )
                }
            }
        } else {
            // GalleryList
            LazyColumn {
                items(dreamImages) { dream ->
                    GalleryListItem(
                        imageUrl = dream.url,
                        prompt = dream.prompt,
                        modifier = Modifier
                            .clickable {
                                onNavigateToDetailScreen(dream)
                            }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {
    // Use Theme here
    GalleryScreen(
        onNavigateToDetailScreen = {},
        onNavigateToAddDreamScreen = {}
    )
}