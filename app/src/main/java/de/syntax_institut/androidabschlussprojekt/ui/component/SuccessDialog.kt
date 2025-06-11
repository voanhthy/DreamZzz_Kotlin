package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        AlertDialog(    
            onDismissRequest = onDismiss,
            modifier = modifier,
            title = { Text(stringResource(R.string.success))},
            text = { Text(stringResource(R.string.image_saved)) },
            confirmButton = {
                Text("OK",
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
private fun SuccessDialogPreview() {
    // Use Theme here
    SuccessDialog(
        showDialog = true,
        onDismiss = {}
    )
}