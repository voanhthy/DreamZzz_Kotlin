package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun FilterButton(
    onClickDropdown: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClickDropdown,
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = stringResource(R.string.filter),
                tint = Color.Black
            )
            Text(stringResource(R.string.filter),
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