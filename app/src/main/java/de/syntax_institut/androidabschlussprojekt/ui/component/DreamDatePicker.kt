package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun DreamDatePicker(
    modifier: Modifier = Modifier
) {
//    val cal = Calendar.getInstance().apply { time = date }
//    Da
}

@Preview(showBackground = true)
@Composable
private fun DreamDatePickerPreview() {
    // Use Theme here
    DreamDatePicker()
}