package de.syntax_institut.androidabschlussprojekt.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.Star
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.getColorForCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.getEmojiForCategory
import java.util.Date

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StarItem(
    dreamImages: List<DreamImage>,
    starsCount: Int,
    onClickDream: (DreamImage) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedImageId by remember { mutableStateOf<String?>(null) }

    // tatsächliche Bildschirmgröße zur Laufzeit
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        val density = LocalDensity.current
        val padding = 32

        // padding sorgt dafür, dass Sterne nicht zu nah am Rand liegen
        val stars = remember(dreamImages) {
            dreamImages.take(starsCount).map { dream ->
                val x = (padding..(width - padding).toInt()).random().toFloat()
                val y = (padding..(height - padding).toInt()).random().toFloat()
                Star(x = x, y = y, dreamImage = dream)
            }
        }

        stars.forEach { star ->
            with(density) {
                val emoji = getEmojiForCategory(star.dreamImage.typeOfDream)
                val color = getColorForCategory(star.dreamImage.typeOfDream)

                IconButton(
                    onClick = {
                        selectedImageId = star.dreamImage.id
                    },
                    modifier = Modifier
                        .offset(x = star.x.toDp(), y = star.y.toDp())
                ) {
                    Text(
                        emoji,
                        color = color
                    )
                }

                if (selectedImageId == star.dreamImage.id) {
                    AsyncImage(
                        model = star.dreamImage.url,
                        contentDescription = "Traumbild",
                        modifier = Modifier
                            .offset(
                                x = (star.x + 80).toDp(),
                                y = (star.y + 70).toDp())
                            .size(70.dp)
                            .zIndex(1f)     // Bild liegt über Overlay
                            .clickable {
                                onClickDream(star.dreamImage)
                            }
                    )
                }

                // Bild ausblenden, wenn außerhalb geklickt wird
                if (selectedImageId != null) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable{ selectedImageId = null }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun StarFieldPreview() {
    // Use Theme here
    StarItem(
        dreamImages = listOf(
            DreamImage(
                id = "1",
                url = "",
                date = Date(),
                prompt = "",
                title = "",
                interpretation = "",
                mood = Mood.GOOD,
                typeOfDream = DreamCategory.NORMAL
            )
        ),
        starsCount = 1,
        onClickDream = {}
    )
}