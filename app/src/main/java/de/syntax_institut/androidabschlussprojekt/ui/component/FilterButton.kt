package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FilterButton(
    onClickDropdown: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClickDropdown,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = "Filter",
                tint = Color.Black
            )
            Text("Filter",
                color = Color.Black)
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun FilterButtonPreview() {
    // Use Theme here
    FilterButton(
        onClickDropdown = {}
    )
}