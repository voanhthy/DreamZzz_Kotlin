package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LogoutButton(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Button(
            onClick = onClickLogout,
            shape = CircleShape,
            modifier = Modifier.size(36.dp),
            contentPadding = PaddingValues(0.dp),      // entfernt Standardabstände
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutButtonPreview() {
    // Use Theme here
    LogoutButton(
        onClickLogout = {}
    )
}