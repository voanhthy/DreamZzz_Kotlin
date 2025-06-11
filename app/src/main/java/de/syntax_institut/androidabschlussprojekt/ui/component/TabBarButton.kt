package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TabBarButton(
    title: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            title.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Light,
            color = if (isActive) Color.White else Color.Black,
        )
    }


}

@Preview(showBackground = true)
@Composable
private fun TabBarButtonPreview() {
    // Use Theme here
    TabBarButton(
        title = "HOME",
        isActive = true,
        onClick = {}
    )
}