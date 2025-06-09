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
fun DeleteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier,
            title = { Text("Traum löschen") },
            text = { Text("Möchtest du diesen Traum wirklich löschen?") },
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
private fun DeleteDialogPreview() {
    // Use Theme here
    DeleteDialog(
        showDialog = true,
        onDismiss = {},
        onConfirm = {}
    )
}