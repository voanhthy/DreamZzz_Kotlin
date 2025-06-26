package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableGalleryListItem(
    dream: DreamImage,
    onSwipeDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { boxValue ->
            when (boxValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    false
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    showDialog = true
                    false
                }

                SwipeToDismissBoxValue.Settled -> {
                    true
                }
            }
        }
    )

    SwipeToDismissBox(
        state = state,
        backgroundContent = {

            val color by animateColorAsState(
                when (state.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.Transparent
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Löschen",
                    tint = Color.Blue
                )
            }
        },
        content = {
            GalleryListItem(
                imageUrl = dream.url,
                prompt = dream.prompt,
                date = dream.date,
                modifier = Modifier
                    .clickable { onClick() }
            )
        },
        modifier = modifier.padding(top = 16.dp)
    )

    DeleteDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            showDialog = true
            onSwipeDelete()
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SwipeableGalleryListItemPreview() {
    // Use Theme here
    SwipeableGalleryListItem(
        dream = DreamImage(
            id = "",
            url = "",
            date = Date(),
            prompt = "",
            title = "",
            interpretation = "",
            mood = Mood.GOOD,
            typeOfDream = DreamCategory.NORMAL
        ),
        onSwipeDelete = {},
        onClick = {}
    )
}