package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.component.AddButton
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamZzzFilterMenu
import de.syntax_institut.androidabschlussprojekt.ui.component.FilterButton
import de.syntax_institut.androidabschlussprojekt.ui.component.SwipeableGalleryListItem
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetailScreen: (DreamImage) -> Unit,
    onNavigateToAddDreamScreen: () -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    var isGrid by remember { mutableStateOf(true) }
    val gridColumns by dreamViewModel.gridColums.collectAsState()

    var showFilter by remember { mutableStateOf(false) }

    val dreams by dreamViewModel.filteredAndSortedDreams.collectAsState()

    if (showFilter) {
        Dialog(
            onDismissRequest = { showFilter = false }       // bei Klick außerhalb schließen
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                DreamZzzFilterMenu(
                    onDissmiss = { showFilter = false }
                )
            }
        }
    }

    if (isGrid) {
        val gridState = rememberLazyStaggeredGridState()

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(gridColumns),
            modifier = modifier
                .fillMaxSize(),
            state = gridState,
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {

//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
//    ) {
            // span: nimmt volle Breite ein
            item(span = StaggeredGridItemSpan.FullLine) {
                Column(
                    modifier = Modifier.fillMaxWidth()
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

                    Text(
                        stringResource(R.string.tab_gallery),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Text(
                        stringResource(R.string.gallery_description),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FilterButton(
                            onClickDropdown = {
                                showFilter = true
                            }
                        )
                    }
                }
            }

//        if (!isGrid) {
//            item {
//                // GalleryGrid
//                LazyVerticalStaggeredGrid(
//                    columns = StaggeredGridCells.Fixed(gridColumns),
//                    modifier = modifier
//                        .fillMaxSize(),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalItemSpacing = 8.dp
//                ) {
            itemsIndexed(dreams) { index, dream ->
                // zufällige Höhe generieren
                val randomHeight = remember(dream.id) {
                    Random.nextInt(80, 400).dp
                }

                AsyncImage(
                    model = dream.url,
                    contentDescription = "Traumbild",
                    modifier = Modifier
                        .clickable {
                            onNavigateToDetailScreen(dream)
                        }
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(randomHeight),
                    contentScale = ContentScale.Crop
                )
            }

        }
    } else {
        // GalleryList
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            item {
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

                Text(
                    stringResource(R.string.tab_gallery),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                Text(
                    stringResource(R.string.gallery_description),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilterButton(
                        onClickDropdown = {
                            showFilter = true
                        }
                    )
                }
            }

            items(dreams) { dream ->

                SwipeableGalleryListItem(
                    dream = dream,
                    onSwipeDelete = {
                        Log.d("GalleryScreen", "Wird gelöscht: ${dream.id}")
                        dreamViewModel.deleteDreamImage(dream)
                    },
                    onClick = {
                        onNavigateToDetailScreen(dream)
                    }
                )
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