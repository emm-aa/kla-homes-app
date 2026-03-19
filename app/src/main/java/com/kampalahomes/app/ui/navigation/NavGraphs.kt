package com.kampalahomes.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.kampalahomes.app.ui.components.BottomNavigationBar
import com.kampalahomes.app.ui.screens.PlaceholderScreen
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.ui.AppStateViewModel
import com.kampalahomes.core.ui.SessionMode
import com.kampalahomes.feature.auth.ui.splash.SplashScreen
import com.kampalahomes.feature.auth.ui.role_selection.RoleSelectionScreen
import com.kampalahomes.feature.auth.ui.auth.AuthScreen
import com.kampalahomes.feature.auth.ui.registration.RegistrationScreen
import com.kampalahomes.feature.onboarding.ui.preference_setup.PreferenceSetupScreen
import com.kampalahomes.feature.onboarding.ui.location_access.LocationAccessScreen
import com.kampalahomes.feature.home.ui.HomeScreen
import com.kampalahomes.feature.profile.ui.ProfileScreen
import com.kampalahomes.feature.property.ui.PropertyDetailsScreen
import com.kampalahomes.core.data.repository.PropertyRepository
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument

fun NavGraphBuilder.startupGraph(
    navController: NavHostController,
    appStateViewModel: AppStateViewModel
) {
    navigation(
        startDestination = AppRoute.Splash.route,
        route = "startup"
    ) {
        composable(route = AppRoute.Splash.route) {
            SplashScreen(navController)
        }

        composable(route = AppRoute.RoleSelection.route) {
            RoleSelectionScreen(navController)
        }

        composable(route = AppRoute.Auth.route) {
            AuthScreen(
                navController = navController,
                onLoginSuccess = {
                    appStateViewModel.updateSessionMode(SessionMode.AUTHENTICATED)
                }
            )
        }

        composable(route = AppRoute.Registration.route) {
            RegistrationScreen(navController)
        }
    }
}

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    appStateViewModel: AppStateViewModel
) {
    navigation(
        startDestination = AppRoute.PreferenceSetup.route,
        route = "onboarding"
    ) {
        composable(route = AppRoute.PreferenceSetup.route) {
            PreferenceSetupScreen(navController)
        }

        composable(route = AppRoute.LocationAccess.route) {
            LocationAccessScreen(
                navController = navController,
                appStateViewModel = appStateViewModel
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    appStateViewModel: AppStateViewModel,
    navController: NavHostController,
    propertyRepository: PropertyRepository
) {
    navigation(
        startDestination = "main_tabs",
        route = "main_flow"
    ) {
        composable(route = "main_tabs") {
            MainTabsScreen(
                appStateViewModel = appStateViewModel,
                navController = navController
            )
        }

        composable(
            route = "${AppRoute.PropertyDetails.route}?propertyId={propertyId}",
            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId")
            val appState by appStateViewModel.appState.collectAsState()
            var property by remember { mutableStateOf<com.kampalahomes.core.model.property.Property?>(null) }

            LaunchedEffect(propertyId) {
                property = propertyId?.let { propertyRepository.getPropertyById(it) }
            }

            property?.let {
                PropertyDetailsScreen(
                    property = it,
                    navController = navController,
                    appState = appState
                )
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        composable(route = AppRoute.AffordabilityCalculator.route) {
            PlaceholderScreen(
                title = "Affordability Calculator",
                subtitle = "Calculate mortgage/loan"
            )
        }

        composable(route = AppRoute.SignInPromptSheet.route) {
            PlaceholderScreen(
                title = "Sign In Prompt",
                subtitle = "Guest gating bottom sheet"
            )
        }

        composable(route = AppRoute.EnquirySentConfirmation.route) {
            PlaceholderScreen(
                title = "Enquiry Sent",
                subtitle = "Confirmation screen"
            )
        }

        composable(route = AppRoute.Notifications.route) {
            PlaceholderScreen(
                title = "Notifications",
                subtitle = "User notifications"
            )
        }

        composable(route = AppRoute.AdvancedFilters.route) {
            PlaceholderScreen(
                title = "Advanced Filters",
                subtitle = "Filter search results"
            )
        }

        composable(route = AppRoute.SearchResults.route) {
            PlaceholderScreen(
                title = "Search Results",
                subtitle = "Filtered properties"
            )
        }

        composable(route = AppRoute.SavedSearches.route) {
            PlaceholderScreen(
                title = "Saved Searches",
                subtitle = "Manage saved searches"
            )
        }

        composable(route = AppRoute.HelpSupport.route) {
            PlaceholderScreen(
                title = "Help & Support",
                subtitle = "FAQ and support"
            )
        }

        composable(route = AppRoute.PrivacyPolicy.route) {
            PlaceholderScreen(
                title = "Privacy Policy",
                subtitle = "Legal policy"
            )
        }

        composable(route = AppRoute.ForgotPassword.route) {
            PlaceholderScreen(
                title = "Forgot Password",
                subtitle = "Reset password"
            )
        }
    }
}

@Composable
fun MainTabsScreen(
    appStateViewModel: AppStateViewModel,
    navController: NavHostController
) {
    val appState by appStateViewModel.appState.collectAsState()
    val tabsNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = tabsNavController,
                appState = appState
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = tabsNavController,
                startDestination = AppRoute.Home.route
            ) {
                composable(route = AppRoute.Home.route) {
                    // Use root nav controller for cross-graph destinations like details/search.
                    HomeScreen(navController)
                }
                composable(route = AppRoute.Map.route) {
                    PlaceholderScreen(
                        title = "Map",
                        subtitle = "Map view with property pins"
                    )
                }
                composable(route = AppRoute.Saved.route) {
                    PlaceholderScreen(
                        title = "Saved Properties",
                        subtitle = "User's saved properties"
                    )
                }
                composable(route = AppRoute.Profile.route) {
                    ProfileScreen(
                        rootNavController = navController,
                        appState = appState,
                        appStateViewModel = appStateViewModel
                    )
                }
            }
        }
    }
}
