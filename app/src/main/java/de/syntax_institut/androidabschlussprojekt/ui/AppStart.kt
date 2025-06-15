package de.syntax_institut.androidabschlussprojekt.ui

import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.TabItem
import de.syntax_institut.androidabschlussprojekt.service.AppNotificationService
import de.syntax_institut.androidabschlussprojekt.ui.component.TabBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.AddDreamScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DreamDetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.GalleryScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.HomeScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.InfoSleepPhaseScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoadingScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.LoginScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.NightSkyScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.PreviewScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.RegisterScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SettingsScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SleepScreen
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

@Serializable
object SleepRoute

@Serializable
object InfoSleepPhaseRoute


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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    // BottomBar ausblenden
    val hideBottomBarRoutes = listOf("RegisterRoute", "LoginRoute", "NightSkyRoute", "LoadingRoute", "PreviewRoute", "SleepRoute")
    val showBottomBar = hideBottomBarRoutes.none { route ->
        currentRoute.contains(route)
    }

    // Benachrichtigungen
    val context = LocalContext.current
    val notificationService = AppNotificationService(context)

    var selectedTabItem by remember { mutableStateOf(TabItem.HOME) }

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

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
            if (showBottomBar) {
                TabBar(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 32.dp),
                    activeTab = selectedTabItem,
                    onTabSelected = { selectedTab ->
                        selectedTabItem = selectedTab
                        navController.navigate(selectedTab.route) {

                            // Tab (BottomBar) wird jedes mal auf Anfang gesetzt
                            popUpTo(MainGraphRoute) {
                                inclusive = false
                                saveState = true        // Zustand des vorherigen Tabs sichern
                            }
                            launchSingleTop = true      // doppelte Instanzen vermeiden
                            restoreState = false
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0,0,0,0),    // verhindert automatische Insets
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
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
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
                        },
                        onNavigateToSleepScreen = {
                            navController.navigate(SleepRoute)
                        },
                        modifier =  Modifier.fillMaxSize()
                    )
                }

                composable<SleepRoute> {
                    SleepScreen(
                        modifier = Modifier.fillMaxSize(),
                        onNavigateToHome = {
                            navController.navigate(HomeRoute)
                        },
                        onNavigateToNightSky = {
                            navController.navigate(NightSkyRoute)
                        },
                        onNavigateToInfoSleepPhase = {
                            navController.navigate(InfoSleepPhaseRoute)
                        }
                    )
                }

                composable<InfoSleepPhaseRoute> {
                    InfoSleepPhaseScreen(
                        onNavigateToHome = {
                            navController.navigate(HomeRoute)
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
