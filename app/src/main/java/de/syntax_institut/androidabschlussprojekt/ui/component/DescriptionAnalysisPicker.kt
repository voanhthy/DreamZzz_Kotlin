package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionAnalysisPicker(
    description: String,
    interpretation: String,
    modifier: Modifier = Modifier
) {
    val showDescription = remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(36.dp)
                .background(Color.LightGray)
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Traumbeschreibung",
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (showDescription.value) Color.White else Color.LightGray)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)      // nimmt nur so viel Höhe wie nötig ein (wrap content) und zentriert Inhalt vertikal
                    .clickable {
                        showDescription.value = true
                    },
            )

            Text("Traumdeutung",
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (!showDescription.value) Color.White else Color.LightGray)
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .clickable { showDescription.value = false },
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = if (showDescription.value) description else interpretation,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailViewTypePickerPreview() {
    // Use Theme here
    DescriptionAnalysisPicker(
        description = "lorem ipsum",
        interpretation = "ipsum lorem"
    )
}