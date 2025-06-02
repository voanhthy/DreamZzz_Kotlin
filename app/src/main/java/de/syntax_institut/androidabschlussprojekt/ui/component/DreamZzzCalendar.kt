package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DreamZzzCalendar(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val today = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { YearMonth.from(today) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val calendar = Calendar.getInstance()
    val monthFormatter = remember {
        DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())
    }


    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Vorheriger Monat"
                )
            }

            Text(
                monthFormatter.format(state.firstVisibleMonth.yearMonth)
            )

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Nächster Monat"
                )
            }
        }

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                val isSelected = day.date == selectedDate.toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val isFromThisMonth = day.position == DayPosition.MonthDate       // innerhalb des aktuellen Monats
                val isFuture = day.date.isAfter(LocalDate.now())
                val enabled = isFromThisMonth && !isFuture

                val textColor = if (isSelected) {
                    Color.White
                } else if (!isFromThisMonth) {
                    Color.Gray
                } else {
                    Color.Black
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) DreamZzzLavender else Color.Transparent
                        )
                        .clickable(
                            enabled = enabled
                        ) {
                            val selected = Calendar.getInstance().apply {
                                set(day.date.year, day.date.monthValue - 1, day.date.dayOfMonth)
                            }.time
                            onDateSelected(selected)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        color = textColor
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarTestPreview() {
    // Use Theme here
    DreamZzzCalendar(
        selectedDate = Date(),
        onDateSelected = { Date() }
    )
}