package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DreamZzzTextButton(
    onClickText: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Button(
            onClick = onClickText,
            modifier = Modifier,
//            enabled = TODO(),
//            shape = TODO(),
//            colors = TODO(),
//            elevation = TODO(),
//            border = TODO(),
//            contentPadding = TODO(),
//            interactionSource = TODO()
        ) {
            Text(title)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview() {
    // Use Theme here
    DreamZzzTextButton(
        onClickText = {},
        title = "Generieren"
    )
}