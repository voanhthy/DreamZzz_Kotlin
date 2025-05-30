package de.syntax_institut.androidabschlussprojekt.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.TabItem
import de.syntax_institut.androidabschlussprojekt.ui.component.TabBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.AddDreamScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DreamDetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.GalleryScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoadingScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoginScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.NightSkyScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.PreviewScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.RegisterScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SettingsScreen
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


// Top Level Routen
@Serializable
object AuthGraphRoute

@Serializable
object MainGraphRoute


// Routen innerhalb MainGraph
@Serializable
object HomeRoute

@Serializable
object GalleryRoute

@Serializable
object SettingsRoute

@Serializable
object AddDreamRoute

@Serializable
object NightSkyRoute

@Serializable
data class PreviewRoute(
    val url: String
)

@Serializable
object LoadingRoute

@Serializable
data class DreamDetailRoute(
    val id: Long
)


// Routen innerhalb AuthGraph
@Serializable
object RegisterRoute

@Serializable
object LoginRoute


@Composable
fun AppStart(
    authViewModel: AuthViewModel = koinViewModel()
) {
    val navController = rememberNavController()

    var selectedTabItem by remember { mutableStateOf(TabItem.HOME) }
    
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()


    if (isLoggedIn) {
        Scaffold(
            bottomBar = {
                TabBar(
                    modifier = Modifier.padding(bottom = 16.dp),
                    activeTab = selectedTabItem,
                    onTabSelected = { selectedTab ->
                        selectedTabItem = selectedTab
                        navController.navigate(selectedTab.route) {
                            // Stack Wachstum bei jedem Tab Klick vermeiden
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true        // Zustand des vorherigen Tabs sichern
                            }
                            launchSingleTop = true      // doppelte Instanzen vermeiden
                            restoreState =
                                true         // alten Zustand beim Zurückspringen wiederherstellen
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
//                HomeScreen(
//                    onClickNavigateToAddDream = {
//                        navController.navigate(AddDreamRoute)
//                    },
//                    onClickNavigateToNightSky = {
//                        navController.navigate(NightSkyRoute)
//                    }
//                )
                    LoginScreen(
                        onNavigateToLoginScreen = {}
                    )
//                NightSkyScreen()
//                RegisterScreen(
//                    onValueChange = {}
//                )
                }

                composable<GalleryRoute> {
                    GalleryScreen(
                        onNavigateToDetailScreen = { dream ->
                            navController.navigate(
                                DreamDetailRoute(
                                    id = dream.id
                                )
                            )
                        },
                        onNavigateToAddDreamScreen = {
                            navController.navigate(AddDreamRoute)
                        }
                    )
                }

                composable<SettingsRoute> {
                    SettingsScreen()
                }

                composable<DreamDetailRoute> {
                    DreamDetailScreen()
                }

                composable<AddDreamRoute> {
                    AddDreamScreen(
                        onClickNavigateToLoadingScreen = {
                            navController.navigate(LoadingRoute)
                        },
                        onClickNavigateToPreviewScreen = { dream ->
                            navController.navigate(
                                PreviewRoute(
                                    url = dream.url
                                )
                            )
                        }
                    )
                }

                composable<NightSkyRoute> {
                    NightSkyScreen()
                }

                composable<RegisterRoute> {
                    RegisterScreen(
                        onNavigateToRegisterScreen = {
                            navController.navigate(RegisterRoute)
                        }
                    )
                }

                composable<LoginRoute> {
                    LoginScreen(
                        onNavigateToLoginScreen = {
                            navController.navigate(LoginRoute)
                        }
                    )
                }

                composable<PreviewRoute> {
                    PreviewScreen()
                }

                composable<LoadingRoute> {
                    LoadingScreen()             // zu PreviewScreen navigieren
                }
            }
        }
    } else {
        RegisterScreen(
            onNavigateToRegisterScreen = {}
        )
    }
}
