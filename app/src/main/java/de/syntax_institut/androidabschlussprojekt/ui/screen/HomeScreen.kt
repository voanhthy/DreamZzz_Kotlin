package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Begrüßungstext
        Text("Hi Gast!",
            fontSize = 36.sp)
        Text("Heute ist Freitag, der 16.05.2025 - ein neuer Tag voller Möglichkeiten. Doch bevor du durchstartest, nimm dir einen Moment für dich.",
            fontSize = 15.sp)

        // interaktiver Nachthimmel
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Blue)
            ) {

            }
        }

        // Kalender
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    // Use Theme here
    HomeScreen()
}