package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.onClick
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.util.Locale

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
            Text(selected.month
                .getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
                .replaceFirstChar { it.uppercase() } + " ${selected.year}")

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
                        Text(month.month
                            .getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
                            .replaceFirstChar { it.uppercase() } + " ${month.year}"
                        )
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