package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray

@Composable
fun NightSkyInfoBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .width(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DreamZzzGray.copy(0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("✨  Normaler Traum",
                style = MaterialTheme.typography.bodyLarge)
            Row {
                Text("★",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red)
                Text("   Albtraum",
                    style = MaterialTheme.typography.bodyLarge)
            }
            Text("\uD83D\uDCA0  Luzider Traum",
                style = MaterialTheme.typography.bodyLarge)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NightSkyInfoBoxPreview() {
    // Use Theme here
    NightSkyInfoBox()
}