package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import de.syntax_institut.androidabschlussprojekt.utils.helper.TextFormatHelper.truncateText


@Composable
fun GalleryListItem(
    imageUrl: String,
    prompt: String,
    date: Date,
    modifier: Modifier = Modifier
) {
    val formattedDate = remember(date) {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bild
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Traumbild",
                    modifier = Modifier
                        .height(100.dp)
                )
                // Beschreibung
                // TODO: Helper Func Text kürzen
                Text(
                    truncateText(prompt, 100),
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Datumanzeige
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(28.dp)
                .offset(x = 25.dp, y = (-12).dp)
                .zIndex(1f)         // über Box herausragen lassen
        ) {
            Text(
                formattedDate,
                modifier = Modifier
                    .width(130.dp)
                    .background(DreamZzzLavender)
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListItemPreview() {
    // Use Theme here
    GalleryListItem(
        imageUrl = "https://www.munich-strategy.com/wp-content/uploads/2023/06/platzhalter.jpg",
        prompt = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
        date = Date()
    )
}