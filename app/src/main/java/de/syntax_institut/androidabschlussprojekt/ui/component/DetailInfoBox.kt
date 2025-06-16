package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailInfoBox(
    dreamImage: DreamImage,
    modifier: Modifier = Modifier
) {
    val date = dreamImage.date
    val formattedDate = remember(date) {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(15.dp))
            .background(DreamZzzGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val title = dreamImage.title

            if (title != null) {
                Text(title,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
            } else {
                Text(stringResource(R.string.unknown_title))
            }

            Row {
                Text(stringResource(R.string.detail_date))
                Text(formattedDate)
            }
            Row {
                Text(stringResource(R.string.type_of_dream_detail))
                Text(stringResource(dreamImage.typeOfDream.titleResId))
            }
            Row {
                Text(stringResource(R.string.todays_mood))
                Text(stringResource(dreamImage.mood.titleResId))
            }
            Text("• • •",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp),
                letterSpacing = 4.sp,
                color = Color.Gray
            )

            // Picker Beschreibung/Interpretation
            DescriptionAnalysisPicker(
                description = dreamImage.prompt,
                interpretation = dreamImage.interpretation.toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailBoxPreview() {
    // Use Theme here
    DetailInfoBox(
        dreamImage = DreamImage(
            url = "lorem ipsum",
            prompt = "lorem ipsum",
            mood = Mood.GOOD,
            typeOfDream = DreamCategory.NORMAL,
            title = null,
            date = Date(),
            interpretation = null
        )
    )
}