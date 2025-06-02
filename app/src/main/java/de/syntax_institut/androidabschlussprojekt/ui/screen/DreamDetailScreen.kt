package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.component.DetailInfoBox
import de.syntax_institut.androidabschlussprojekt.ui.component.TabBarButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamDetailViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun DreamDetailScreen(
    modifier: Modifier = Modifier,
    dreamDetailViewModel: DreamDetailViewModel = koinViewModel()
) {
    val dreamImage by dreamDetailViewModel.dreamStateFlow.collectAsState()

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // generiertes Bild
            item {
                dreamImage?.let { image ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = image.url,
                            contentDescription = "Bild",
                            modifier = Modifier
                                .fillMaxWidth(),
//                                .height(300.dp),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
//                                .matchParentSize()
                                .height(40.dp)
                                .align(Alignment.BottomCenter)
//                                .offset(y = 100.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
//                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.White.copy(0.2f),
                                            Color.White.copy(0.5f),
                                            Color.White.copy(0.8f),
                                            Color.White,
                                        ),
//                                        startY = 0f,
//                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                    }
                }
            }

            // Box mit Infos
            item {
                dreamImage?.let { image ->
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(15.dp))
//                        .background(DreamZzzGray)
//                ) {
//                    Text(image.prompt,
//                        modifier = Modifier.padding(16.dp))
//
//                }
                    Spacer(modifier = Modifier.padding(16.dp))

                    DetailInfoBox(
                        image,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DreamDetailScreenPreview() {
    // Use Theme here
    DreamDetailScreen()
}