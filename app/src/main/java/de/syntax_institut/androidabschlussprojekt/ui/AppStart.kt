package de.syntax_institut.androidabschlussprojekt.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.syntax_institut.androidabschlussprojekt.ui.screen.AddDreamScreen

@Composable
fun AppStart() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        AddDreamScreen(
            modifier = Modifier.padding(innerPadding)
        )
    }
}
