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

    val numYAxisLabels = if (maxCount.toInt() % 2 == 0) {
        6       // Wenn gerade, 6 Labels
    } else {
        5       // Wenn ungerade, 5 Labels
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

//        // Linien und Y-Achse Offset
//        val yAxisStartX = 0f                        // Y-Achse startet links am Canvas-Rand
//        val xAxisStartY = chartHeight        // X-Achse startet unten am Canvas-Rand
//
//        drawLine(
//            color = Color.LightGray,                // Farbe der Y-Achse
//            start = Offset(yAxisStartX, 0f),        // Oben links
//            end = Offset(yAxisStartX, xAxisStartY), // Unten links
//            strokeWidth = 2f
//        )

        // Punkte Berechnung
        val points = moodsSorted.mapIndexed { index, mood ->
            val count = moodCounts[mood.value] ?: 0
            val x = index * pointDistance
            val y = chartHeight - ((count / maxCount) * chartHeight)
            Offset(x, y)
        }

        // Verbindungslinien zeichnen
        for (i in 0 until points.size - 1) {
            drawLine(
                color = DreamZzzLavender,
                start = points[i],
                end =  points[i + 1],
                strokeWidth = 6f
            )
        }


        for (i in 0 until numYAxisLabels) {
            val labelValue = (maxCount / (numYAxisLabels - 1)) * i
            val yPos = chartHeight - (labelValue / maxCount) * chartHeight

            // Zahlen für y Achse
//            drawContext.canvas.nativeCanvas.drawText(
//                "${labelValue.toInt()}",    // Label als Integer anzeigen
//                yAxisStartX - 25f,          // Etwas links von der Y-Achsenlinie
//                yPos + (28f / 2),           // Text mittig zur Linie ausrichten (textSize / 2)
//                android.graphics.Paint().apply {
//                    textAlign = android.graphics.Paint.Align.RIGHT // Text rechtsbündig zur Linie
//                    textSize = 28f
//                    color = android.graphics.Color.DKGRAY
//                }
//            )
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

//            val count = moodCounts[mood.value] ?: 0
//            drawContext.canvas.nativeCanvas.drawText(
//                count.toString(), // Die Anzahl als String
//                point.x,
//                point.y - 30f, // Positionierung 30f Pixel über dem Punkt
//                android.graphics.Paint().apply {
//                    textAlign = android.graphics.Paint.Align.CENTER
//                    textSize = 28f // Gleiche Textgröße wie Mood-Labels
//                    color = android.graphics.Color.BLACK // Farbe des Textes
//                    // Füge hier weitere Paint-Eigenschaften hinzu, falls gewünscht, z.B. Typeface
//                }
//            )
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