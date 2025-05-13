package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TabBarButton(
    title: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(110.dp),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Green
        )
    ) {
        Text(
            title,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Light,
//            color = if (active) Color.White else Color.Black,
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun TabBarButtonPreview() {
    // Use Theme here
    TabBarButton(
        title = "HOME",
        active = true,
        onClick = {}
    )
}