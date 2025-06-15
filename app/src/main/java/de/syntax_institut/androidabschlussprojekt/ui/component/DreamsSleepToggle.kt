package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.contentDescription
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun DreamsSleepToggle(
    dreams: String,
    sleep: String,
    modifier: Modifier = Modifier,
    onSleepSelected: () -> Unit = {}
) {
    val isSleepSelected = remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .width(180.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Hintergrundbild
        Image(
            painter = if (isSleepSelected.value) painterResource(R.drawable.sleep) else painterResource(
                R.drawable.sky
            ),
            contentDescription = "",
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(50.dp)),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = modifier
                .matchParentSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isSleepSelected.value) Color.White.copy(alpha = 0.9f) else Color.Transparent)
                    .clickable { isSleepSelected.value = true }
            ) {
                Text(
                    "Träume".uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (!isSleepSelected.value) Color.White.copy(alpha = 0.9f) else Color.Transparent)
                    .clickable {
                        isSleepSelected.value = false
                        onSleepSelected()
                    }
            ) {
                Text(
                    "Schlaf".uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))

    Text(
        text = if (isSleepSelected.value) dreams else sleep,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Justify
    )
}


@Preview(showBackground = true)
@Composable
private fun DreamsSleepTogglePreview() {
    // Use Theme here
    DreamsSleepToggle(
        dreams = "",
        sleep = ""
    )
}