package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun ImageStylePicker(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ImageStyle.entries) { style ->
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(style.drawableResId),
                    contentDescription = stringResource(style.titleResId),
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(8.dp))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(
                            color = Color.White.copy(0.6f),
                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(style.titleResId),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageStylePickerPreview() {
    // Use Theme here
    ImageStylePicker()
}