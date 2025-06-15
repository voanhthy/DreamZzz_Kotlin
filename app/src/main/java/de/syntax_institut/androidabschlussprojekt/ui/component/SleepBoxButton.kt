package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.subtitle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun SleepBoxButton(
    title: String,
    subtitle: String,
    backgroundImageResId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .height(180.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(backgroundImageResId),
            contentDescription = "Hintergrundbild",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .alpha(0.9f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(6.dp))

            Text(title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 26.sp,
//                modifier = Modifier.weight(1f)
            )

            Text(subtitle,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                lineHeight = 13.sp,
                textAlign = TextAlign.Center
//                modifier = Modifier.weight(1f)
            )
        }
    }

//    ElevatedButton(
//        onClick = {},
//        modifier = modifier
////            .clip(RoundedCornerShape(0.dp))
//            .height(180.dp),
//        shape = RoundedCornerShape(10.dp),
//        colors = ButtonDefaults.elevatedButtonColors(
//            containerColor = DreamZzzGray.copy(0.7f)
//        ),
//        elevation = ButtonDefaults.elevatedButtonElevation(
//            defaultElevation = 0.dp),
//        contentPadding = PaddingValues(6.dp)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Spacer(modifier = Modifier.padding(6.dp))
//
//            Text(title,
//                style = MaterialTheme.typography.titleLarge,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.SemiBold,
//                lineHeight = 26.sp,
////                modifier = Modifier.weight(1f)
//            )
//
//            Text(subtitle,
//                style = MaterialTheme.typography.bodyLarge,
//                fontWeight = FontWeight.Light,
//                fontSize = 12.sp,
//                lineHeight = 13.sp,
//                textAlign = TextAlign.Center
////                modifier = Modifier.weight(1f)
//            )
//        }
//    }
}

@Preview(showBackground = true)
@Composable
private fun SleepBoxPreview() {
    // Use Theme here
    SleepBoxButton(
        title = "Schlaf",
        subtitle = "Erforsche die Zyklen deiner Nacht",
        backgroundImageResId = R.drawable.background1,
        onClick = {}
    )
}