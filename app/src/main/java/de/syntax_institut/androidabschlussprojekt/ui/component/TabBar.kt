package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TabBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .width(340.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TabBarButton(
                title = "HOME",
                active = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            TabBarButton(
                title = "GALLERY",
                active = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )

            TabBarButton(
                title = "YOU",
                active = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabBarPreview() {
    // Use Theme here
    TabBar()
}