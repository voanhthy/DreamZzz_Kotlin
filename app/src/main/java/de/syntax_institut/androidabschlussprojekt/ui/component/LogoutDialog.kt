package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    showDialog: Boolean,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier,
            title = { Text("Abmelden") },
            text = { Text("Möchtest du dich wirklich abmelden?") },
            confirmButton = {
                Text(
                    "OK",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onConfirm() }
                )
            },
            dismissButton = {
                Text(
                    "Abbrechen",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onDismiss() }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoutDialogPreview() {
    // Use Theme here
    LogoutDialog(
        onDismiss = {},
        onConfirm = {},
        showDialog = true
    )
}