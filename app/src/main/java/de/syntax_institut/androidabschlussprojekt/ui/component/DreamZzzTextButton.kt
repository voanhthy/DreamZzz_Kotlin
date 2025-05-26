package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DreamZzzTextButton(
    onClickText: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickText,
        modifier = modifier,
//            enabled = TODO(),
            shape = RoundedCornerShape(10.dp),
//            colors = TODO(),
//            elevation = TODO(),
//            border = TODO(),
//            contentPadding = TODO(),
//            interactionSource = TODO()
    ) {
        Text(title)
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