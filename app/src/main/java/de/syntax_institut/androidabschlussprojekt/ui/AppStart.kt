package de.syntax_institut.androidabschlussprojekt.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.TabItem
import de.syntax_institut.androidabschlussprojekt.ui.component.TabBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.AddDreamScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.GalleryScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute

@Serializable
object GalleryRoute

@Serializable
object SettingsRoute

@Composable
fun AppStart() {
    val navController = rememberNavController()

    var selectedTabItem by remember { mutableStateOf(TabItem.HOME) }

    Scaffold(
        bottomBar = {
            TabBar(
                activeTab = selectedTabItem,
                onTabSelected = { selectedTab ->
                    navController.navigate(selectedTab.route) {
                        // Stack Wachstum bei jedem Tab Klick vermeiden
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true        // Zustand des vorherigen Tabs sichern
                        }
                        launchSingleTop = true      // doppelte Instanzen vermeiden
                        restoreState = true         // alten Zustand beim Zurückspringen wiederherstellen
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = selectedTabItem.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                HomeScreen()
            }

            composable<GalleryRoute> {
                GalleryScreen()
            }

            composable<SettingsRoute> {
                AddDreamScreen()
            }
        }
    }
}
