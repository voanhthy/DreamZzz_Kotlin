package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

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
            modifier = modifier,
            title = { Text(stringResource(R.string.logout)) },
            text = { Text(stringResource(R.string.logout_confirm)) },
            confirmButton = {
                Text(
                    stringResource(R.string.ok),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onConfirm() }
                )
            },
            dismissButton = {
                Text(
                    stringResource(R.string.cancel),
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