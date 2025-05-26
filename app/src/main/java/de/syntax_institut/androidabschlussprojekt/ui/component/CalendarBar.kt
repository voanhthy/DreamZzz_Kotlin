package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarBar(
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    // heute
    val today = LocalDate.now()
    val daysRange = (-4..0).toList()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysRange.forEach { day ->
            val date = today.plusDays(day.toLong())

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    date.dayOfWeek
                        .getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        .uppercase(Locale.getDefault())
                        .trimEnd('.')
                )

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(if (date == selectedDate) DreamZzzLavender else Color.Transparent)
                        .clickable { onSelectedDate(date) }
                        .padding(8.dp)
                ) {
                    Text(
                        date.dayOfMonth.toString()
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CalendarBarPreview() {
    // Use Theme here
    CalendarBar(
        selectedDate = LocalDate.now(),
        onSelectedDate = {}
    )
}