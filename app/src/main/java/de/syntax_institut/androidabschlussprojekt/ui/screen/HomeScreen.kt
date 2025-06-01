package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.AddButton
import de.syntax_institut.androidabschlussprojekt.ui.component.CalendarBar
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.pluralStringResource
import de.syntax_institut.androidabschlussprojekt.ui.component.Greeting


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickNavigateToAddDream: () -> Unit,
    onClickNavigateToNightSky: () -> Unit,
    dreamViewModel: DreamViewModel = koinViewModel()
) {
    val date by dreamViewModel.date.collectAsState()
    val datesWithDreams by dreamViewModel.datesWithDreams.collectAsState()
    val dreamCount by dreamViewModel.dreamCount.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Add Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            AddButton(
                onAddClick = onClickNavigateToAddDream
            )
        }

        // Begrüßungstext
        Greeting()

        // interaktiver Nachthimmel
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    onClickNavigateToNightSky()
                }
        ) {
            Image(
                painter = painterResource(R.drawable.sky),
                contentDescription = "Night Sky",
                modifier = Modifier.matchParentSize(),       // füllt die ganze Box aus
                contentScale = ContentScale.Crop
            )

            Text(
                pluralStringResource(R.plurals.dream_count_plurals, dreamCount, dreamCount),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)
            )
        }

        // Kalender
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp)
        )
        CalendarBar(
            selectedDate = date,
            onSelectedDate = { dreamViewModel.updateDate(it) },
            datesWithDreams = datesWithDreams
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    // Use Theme here
    HomeScreen(
        onClickNavigateToAddDream = {},
        onClickNavigateToNightSky = {}
    )
}