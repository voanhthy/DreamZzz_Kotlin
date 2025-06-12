package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzDarkGray
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoodLineChart(
    moodCounts: Map<Int, Int>,
    modifier: Modifier = Modifier
) {
    // Mood enum nach ihrem value (1 bis 5) sortieren
    val moodsSorted = Mood.values().sortedBy { it.value }
    // max. Zählerstand für y-Achse Skalierung
    val maxCount = (moodCounts.values.maxOrNull() ?: 1).toFloat()

    val moodLabels = moodsSorted.associateWith { mood ->
        stringResource(id = mood.titleResId)
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp)
    ) {
        val chartWidth = size.width
        val chartHeight = size.height
        // Abstand zwischen Punkten auf der x-Achse
        val pointDistance = chartWidth / (moodsSorted.size - 1).coerceAtLeast(1)

        // Punkte Berechnung
        val points = moodsSorted.mapIndexed { index, mood ->
            val count = moodCounts[mood.value] ?: 0
            val x = index * pointDistance
            val y = chartHeight - ((count / maxCount) * chartHeight)
            Offset(x, y)
        }

        // Linien zeichnen
        for (i in 0 until points.size - 1) {
            drawLine(
                color = DreamZzzLavender,
                start = points[i],
                end =  points[i + 1],
                strokeWidth = 6f
            )
        }

        // Punkte + x-Achse Labels
        points.forEachIndexed { index, point ->
            drawCircle(
                color = Color.Blue,
                radius = 6f,
                center = point
            )

            val mood = moodsSorted[index]
            val label = moodLabels[mood] ?: mood.name

            drawContext.canvas.nativeCanvas.drawText(
                label,
                point.x,
                size.height + 50f,
                android.graphics.Paint().apply {
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 28f
                    color = android.graphics.Color.BLACK
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoodLineChartPreview() {
    // Use Theme here
    MoodLineChart(
        moodCounts = mapOf(1 to 4, 2 to 6, 3 to 2, 4 to 1, 5 to 5)
    )
}