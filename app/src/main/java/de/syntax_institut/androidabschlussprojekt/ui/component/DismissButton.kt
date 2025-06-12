package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.onClick
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DismissButton(
    onClickDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickDismiss,
        shape = CircleShape,
        modifier = Modifier.size(36.dp),
        contentPadding = PaddingValues(0.dp),      // entfernt Standardabstände
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DismissButtonPreview() {
    // Use Theme here
    DismissButton(
        onClickDismiss = {}
    )
}