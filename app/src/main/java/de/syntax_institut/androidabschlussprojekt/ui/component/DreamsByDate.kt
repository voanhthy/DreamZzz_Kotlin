package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage


@Composable
fun DreamsByDate(
    modifier: Modifier = Modifier,
    dreams: List<DreamImage>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (dreams.isEmpty()) {
            // TODO: placeholder image
        } else {
            dreams.forEach { dream ->

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .width(150.dp)
                            .background(Color.Green)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Titel
                        Text(
                            "Titel".uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 16.sp
                        )

                        // Beschreibung
                        Text(
                            "lorem ipsum",
                            style = MaterialTheme.typography.bodyLarge,
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
        dreams = emptyList()
    )
}