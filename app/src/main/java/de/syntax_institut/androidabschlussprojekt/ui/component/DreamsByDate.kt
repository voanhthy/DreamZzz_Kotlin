package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.utils.helper.TextFormatHelper.truncateText


@Composable
fun DreamsByDate(
    modifier: Modifier = Modifier,
    dreams: List<DreamImage>,
    onClickDream: (DreamImage) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (dreams.isEmpty()) {
            Text(stringResource(R.string.no_dream_for_date),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp))
        } else {
            dreams.forEach { dream ->

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onClickDream(dream) },
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Titel
                        Text(
                            dream.title.toString().uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )

                        // Beschreibung
                        Text(
                            truncateText(dream.prompt, 80),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                    }

                    // Bild
                    AsyncImage(
                        model = dream.url,
                        contentDescription = "generiertes Bild",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .weight(1f),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DreamsByDatePreview() {
    // Use Theme here
    DreamsByDate(
        dreams = emptyList(),
        onClickDream = {}
    )
}