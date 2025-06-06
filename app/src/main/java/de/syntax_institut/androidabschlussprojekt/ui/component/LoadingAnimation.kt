package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Transition")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000)
        )
    )

    val paddingPercentageOuterCircle = 0.15f
    val paddingPercentageInnerCircle = 0.3f
    val positionStartOffsetOuterCircle = 90f
    val positionStartOffsetInnerCircle = 135f

    var width by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .size(40.dp)
            .onSizeChanged {
                width = it.width
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 1.dp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    rotationZ = rotation
                )
        )

        CircularProgressIndicator(
            strokeWidth = 1.dp,
            color = Color.Blue,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * paddingPercentageInnerCircle).toDp()
                    }
                )
                .graphicsLayer(
                    rotationZ = rotation + positionStartOffsetInnerCircle
                )
        )

        CircularProgressIndicator(
            strokeWidth = 1.dp,
            color = DreamZzzLavender,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    with(LocalDensity.current) {
                        (width * paddingPercentageOuterCircle).toDp()
                    }
                )
                .graphicsLayer(
                    rotationZ = rotation + positionStartOffsetOuterCircle
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingAnimationPreview() {
    // Use Theme here
    LoadingAnimation(
        modifier = Modifier.size(100.dp)
    )
}