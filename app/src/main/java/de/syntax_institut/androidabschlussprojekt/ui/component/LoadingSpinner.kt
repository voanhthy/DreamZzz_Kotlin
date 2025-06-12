package de.syntax_institut.androidabschlussprojekt.ui.component

import android.os.Build
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier
) {
    // You'll likely want to adjust the size
    val animationModifier = modifier.size(310.dp) // Example size: 120dp by 120dp

    val context = LocalContext.current // Get the context

    // Conditionally set the decoder factory based on the Android API level
    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading) // Replace 'loading_spinner' with your GIF file name
        .decoderFactory(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // P is API level 28
                ImageDecoderDecoder.Factory() // Use ImageDecoder for API 28+
            } else {
                GifDecoder.Factory() // Use GifDecoder for API < 28
            }
        )
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = "Lade Animation", // Describe what the animation is for accessibility
        modifier = animationModifier
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingSpinnerPreview() {
    // Use Theme here
    LoadingSpinner()
}