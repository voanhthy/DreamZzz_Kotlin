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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.TabItem
import de.syntax_institut.androidabschlussprojekt.ui.LoginRoute
import de.syntax_institut.androidabschlussprojekt.ui.component.TabBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.AddDreamScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DreamDetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.GalleryScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.HomeScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoadingScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoginScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.NightSkyScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.PreviewScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.RegisterScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SettingsScreen
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.AuthViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
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
    val id: String
)

@Serializable
object LoadingRoute

@Serializable
data class DreamDetailRoute(
    val id: String
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

    val loginSuccess by authViewModel.loginSuccess.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(MainGraphRoute) {
                popUpTo(AuthGraphRoute) {
                    inclusive = true
                }    // gesamtem Authentifizierungs-Stack entfernen
            }
            authViewModel.resetAuthStates()
        }
    }

    Scaffold(
        bottomBar = {
            TabBar(
                modifier = Modifier.padding(bottom = 16.dp),
                activeTab = selectedTabItem,
                onTabSelected = { selectedTab ->
                    selectedTabItem = selectedTab
                    navController.navigate(selectedTab.route) {
                        // Stack Wachstum bei jedem Tab Klick vermeiden
//                        popUpTo(navController.graph.startDestinationId) {
//                            saveState = true        // Zustand des vorherigen Tabs sichern
//                        }
//                        launchSingleTop = true      // doppelte Instanzen vermeiden
//                        restoreState =
//                            true                    // alten Zustand beim Zurückspringen wiederherstellen

                        // besser: Tab wird jedes mal auf Anfang gesetzt
                        popUpTo(MainGraphRoute) { saveState = false }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) MainGraphRoute else AuthGraphRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Top Level Route - AuthGraphRoute
            navigation<AuthGraphRoute>(startDestination = LoginRoute) {
                composable<RegisterRoute> {
                    RegisterScreen(
                        onNavigateToLoginScreen = {
                            navController.navigate(LoginRoute)
                        }
                    )
                }

                composable<LoginRoute> {
                    LoginScreen(
                        onNavigateToRegisterScreen = {
                            navController.navigate(RegisterRoute)
                        },
                        onNavigateToHomeScreen = {
                            navController.navigate(HomeRoute)
                        }
                    )
                }
            }

            // Top Level Route - MainGraphRoute
            navigation<MainGraphRoute>(startDestination = HomeRoute) {
                composable<HomeRoute> {
                    HomeScreen(
                        onClickNavigateToAddDream = {
                            navController.navigate(AddDreamRoute)
                        },
                        onClickNavigateToNightSky = {
                            navController.navigate(NightSkyRoute)
                        },
                        onClickNavigateToDreamDetail = { dream ->
                            navController.navigate(
                                DreamDetailRoute(
                                    id = dream.id
                                )
                            )
                        }
                    )
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
                        }
                    )
                }

                composable<NightSkyRoute> {
                    NightSkyScreen(
                        onNavigateToDreamDetail = { dream ->
                            navController.navigate(
                                DreamDetailRoute(
                                    id = dream.id
                                )
                            )
                        }
                    )
                }

                composable<PreviewRoute> {
                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AddDreamRoute)
                    }
                    val sharedViewModel: DreamViewModel =
                        koinViewModel(viewModelStoreOwner = parentEntry)

                    PreviewScreen(
                        dreamViewModel = sharedViewModel,
                        onNavigateToDreamDetail = { dream ->
                            navController.navigate(
                                DreamDetailRoute(
                                    id = dream.id
                                )
                            )
                        }
                    )
                }

                composable<LoadingRoute> {
                    val parentEntry = remember(it) {
                        navController.getBackStackEntry(AddDreamRoute)
                    }
                    // Zustand bleibt erhalten
                    val sharedViewModel: DreamViewModel =
                        koinViewModel(viewModelStoreOwner = parentEntry)

                    LoadingScreen(
                        dreamViewModel = sharedViewModel,
                        onNavigateToPreview = { dream ->
                            navController.navigate(
                                PreviewRoute(
                                    id = dream.id
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
