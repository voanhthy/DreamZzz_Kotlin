package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue


@Composable
fun Greeting(
    userProfileViewModel: UserProfileViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val firstName by userProfileViewModel.firstName.collectAsState()
    val displayName = firstName ?: stringResource(R.string.guest)

    val today = remember { LocalDate.now() }

    val weekdayName = remember {
        today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("de"))
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("de")) else it.toString() }
    }
    
    val pattern = stringResource(R.string.pattern_date)

    val formattedDate = remember {
        today.format(DateTimeFormatter.ofPattern(pattern, Locale("de")))
    }


    Column {
        Text(stringResource(R.string.welcome_with_name, displayName),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            stringResource(R.string.greeting_text, weekdayName, formattedDate),
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    // Use Theme here
    Greeting()
}