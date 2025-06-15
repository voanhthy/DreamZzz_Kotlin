package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SleepViewModel
import de.syntax_institut.androidabschlussprojekt.utils.helper.MonthDisplayNameHelper.getMonthDisplayName
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth

@Composable
fun MonthDropdownMenu(
    months: List<YearMonth>,
    selected: YearMonth,
    onSelect: (YearMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(getMonthDisplayName(selected),
                fontWeight = FontWeight.SemiBold)

            Icon(Icons.Default.KeyboardArrowDown,
                contentDescription = "Monat wählen")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    text = {
                        Text(getMonthDisplayName(month))    // übergibt den ausgewählten Monat (oder den Sentinel)
                    },
                    onClick = {
                        onSelect(month)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthDropdownMenuPreview() {
    // Use Theme here
    MonthDropdownMenu(
        months = listOf(),
        selected = YearMonth.now(),
        onSelect = {}
    )
}