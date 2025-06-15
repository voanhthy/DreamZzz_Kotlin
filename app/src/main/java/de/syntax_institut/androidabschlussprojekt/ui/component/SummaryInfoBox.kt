package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SummaryInfoBox(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text("So war dein Monat")
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryInfoBoxPreview() {
    // Use Theme here
    SummaryInfoBox()
}