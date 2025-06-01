package de.syntax_institut.androidabschlussprojekt.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("RememberReturnType")
@Composable
fun CalendarBar(
    selectedDate: Date,
    onSelectedDate: (Date) -> Unit,
    datesWithDreams: Set<Long>,         // Set von Dates, an denen Träume eingetragen wurden
    modifier: Modifier = Modifier
) {
    // heute
    val today = remember { Calendar.getInstance() }
    val daysRange = (-4..0).toList()        // 4 Tage vor heute

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysRange.forEach { dayOffset ->
            val calendar = remember { Calendar.getInstance() }

            calendar.time = today.time
            calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
            val dateToDisplay = calendar.time

            val dayOfWeekFormatter = remember { SimpleDateFormat("EE", Locale.getDefault()) }
            val dayOfMonthFormatter = remember { SimpleDateFormat("dd", Locale.getDefault()) }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(dayOfWeekFormatter.format(dateToDisplay).uppercase(Locale.getDefault()).trimEnd('.'),
                    style = MaterialTheme.typography.labelSmall)

                val isSelected = remember(selectedDate, dateToDisplay) {
                    val date1 = Calendar.getInstance().apply { time = selectedDate }
                    val date2 = Calendar.getInstance().apply { time = dateToDisplay }
                    date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                    date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR)
                }
                
                // falls Date Traum gespeichert hat, kleinen Punkt anzeigen
                val dateWithDream = remember(dateToDisplay, datesWithDreams) {
                    val calendar = Calendar.getInstance().apply { time = dateToDisplay }
                    // für den Vergleich Std, Min, Sek, Millisek auf 0 setzen
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    datesWithDreams.contains(calendar.timeInMillis)
                }
                
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(shape = CircleShape)
                        .background(if (isSelected) DreamZzzLavender else Color.Transparent)
                        .clickable { onSelectedDate(dateToDisplay) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            dayOfMonthFormatter.format(dateToDisplay),
                            fontSize = 13.sp
                        )

                        // kleiner Punkt
                        Box(
                            modifier = Modifier
                                .padding(1.dp)
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(if (dateWithDream) Color.Gray else Color.Transparent)     
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarBarPreview() {
    // Use Theme here
    val today = Date()
    val yesterday = Date(today.time - 86_400_000L) // 1 Tag in ms

    CalendarBar(
        selectedDate = Date(),
        onSelectedDate = {},
        datesWithDreams = setOf(today.time, yesterday.time)
    )
}