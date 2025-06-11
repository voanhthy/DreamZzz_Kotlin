package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.lazy.items

@Composable
fun ImageStylePicker(
    selectedStyle: ImageStyle,
    onClick: (ImageStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(ImageStyle.entries) {currentStyle ->

            val isSelected = currentStyle == selectedStyle
            val borderColor = if (isSelected) Color.Blue else Color.Transparent
            val borderWidth = if (isSelected) 2.dp else 0.dp

            Box(
                modifier = Modifier.size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onClick(currentStyle)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(currentStyle.drawableResId),
                    contentDescription = stringResource(currentStyle.titleResId),
                    modifier = Modifier
                        .matchParentSize()
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
                    Text(stringResource(currentStyle.titleResId),
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
    ImageStylePicker(
        selectedStyle = ImageStyle.ABSTRACT,
        onClick = {}
    )
}