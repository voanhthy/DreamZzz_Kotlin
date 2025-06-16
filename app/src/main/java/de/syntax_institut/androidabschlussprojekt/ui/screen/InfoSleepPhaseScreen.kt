package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.R.attr.end
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.component.DismissButton

@Composable
fun InfoSleepPhaseScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(0.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    DismissButton(
                        onClickDismiss = { onNavigateToHome() }
                    )
                }

                Text(
                    stringResource(R.string.sleep_phases_dreams),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Text(
                    "Unser Schlaf ist keine einfache, statische Ruhephase, sondern ein komplexer Kreislauf, in dem unser Körper und Geist unterschiedliche Aufgaben erfüllen. Eine typische Nacht besteht aus mehreren Schlafzyklen, die jeweils etwa 90 bis 120 Minuten dauern und sich aus verschiedenen Phasen zusammensetzen.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Justify
                )

                Text(
                    "Die Schlafphasen",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    "1. NREM-Schlaf (Non-Rapid Eye Movement-Schlaf): Dieser macht den Großteil unseres Schlafs aus (etwa 75-80%) und wird in drei Stadien unterteilt:\n" +
                            "\n" +
                            "N1 (Leichtschlaf): Dies ist die Einschlafphase, in der wir noch leicht aufzuwecken sind. Die Muskeln entspannen sich, und Herzschlag sowie Atmung verlangsamen sich.\n" +
                            "N2 (Stabiler Leichtschlaf): In diesem Stadium verbringen wir die meiste Zeit. Die Körpertemperatur sinkt weiter, die Herz- und Atemfrequenz werden langsamer. Gehirnwellen zeigen charakteristische \"Schlafspindeln\" und K-Komplexe, die vermutlich eine Rolle bei der Gedächtniskonsolidierung spielen.\n" +
                            "N3 (Tiefschlaf oder Slow-Wave Sleep - SWS): Dies ist die erholsamste Phase des Schlafs. Der Körper repariert sich, baut Muskeln auf und stärkt das Immunsystem. Herzschlag und Atmung erreichen ihre niedrigsten Raten, und es ist am schwierigsten, aus diesem Schlaf aufzuwachen."
                )

                Text(
                    "2. REM-Schlaf (Rapid Eye Movement-Schlaf): Diese Phase ist durch schnelle Augenbewegungen (daher der Name), eine erhöhte Gehirnaktivität (ähnlich dem Wachzustand) und eine vorübergehende Lähmung der Skelettmuskulatur gekennzeichnet.\n" +
                            "\n" +
                            "Der erste REM-Schlaf tritt etwa 60 bis 90 Minuten nach dem Einschlafen auf und wird im Laufe der Nacht immer länger.\n" +
                            "Im REM-Schlaf träumen wir am intensivsten und lebhaftesten."
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoSleepPhaseScreenPreview() {
    // Use Theme here
    InfoSleepPhaseScreen(
        onNavigateToHome = {}
    )
}