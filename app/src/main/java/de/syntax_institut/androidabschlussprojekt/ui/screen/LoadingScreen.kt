package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text("Ich bin ein LadeScreen")
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    // Use Theme here
    LoadingScreen()
}