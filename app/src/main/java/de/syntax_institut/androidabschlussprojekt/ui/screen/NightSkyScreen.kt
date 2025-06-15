package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.ui.component.DreamsSleepToggle
import de.syntax_institut.androidabschlussprojekt.ui.component.NightSkyInfoBox
import de.syntax_institut.androidabschlussprojekt.ui.component.SleepScreenButton
import de.syntax_institut.androidabschlussprojekt.ui.component.StarItem
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import de.syntax_institut.androidabschlussprojekt.utils.helper.enableFullscreen
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NightSkyScreen(
    dreamViewModel: DreamViewModel = koinViewModel(),
    onNavigateToDreamDetail: (DreamImage) -> Unit,
    onNavigateToSleepScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showInfoBox by remember { mutableStateOf(false) }
    val dreamImages by dreamViewModel.savedDreamImagesState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        (context as? Activity)?.enableFullscreen(true)
    }

    DisposableEffect(Unit) {
        onDispose {
            (context as? Activity)?.enableFullscreen(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.sky),
            contentDescription = "Night Sky",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LaunchedEffect(dreamImages) {
            Log.d("NightSky", "DreamImages: ${dreamImages.size}")
        }

        StarItem(
            dreamImages = dreamImages,
            starsCount = dreamImages.size,
            onClickDream = onNavigateToDreamDetail,
            modifier = Modifier
                .fillMaxSize()
                .size(50.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            // Help Button
            // TODO: Animation hinzufügen
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    showInfoBox = !showInfoBox      // Toggle
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                    contentDescription = "Help Button",
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {

            SleepScreenButton(
                onClick = { onNavigateToSleepScreen() }
            )
        }
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 16.dp)
//        ) {
//            DreamsSleepToggle(
//                dreams = "",
//                sleep = "",
//                onSleepSelected = { onNavigateToSleepScreen() }
//            )
//        }

        if (showInfoBox) {
            NightSkyInfoBox(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 64.dp, end = 48.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun NightSkyScreenPreview() {
//    // Use Theme here
//    NightSkyScreen(
//        onNavigateToDreamDetail = {}
//    )
//}