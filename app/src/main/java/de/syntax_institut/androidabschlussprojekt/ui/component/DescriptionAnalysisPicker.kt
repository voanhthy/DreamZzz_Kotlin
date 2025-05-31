package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionAnalysisPicker(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .height(40.dp)
            .background(Color.LightGray)
            .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Traumbeschreibung",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
        )

        Text("Traumdeutung",
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailViewTypePickerPreview() {
    // Use Theme here
    DescriptionAnalysisPicker()
}