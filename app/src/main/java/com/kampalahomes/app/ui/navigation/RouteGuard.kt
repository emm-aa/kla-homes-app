package com.kampalahomes.app.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.ui.AppState

object RouteGuard {

    fun checkGuestAccess(
        navController: NavController,
        appState: AppState,
        gatedRoutes: List<String> = listOf(
            AppRoute.Saved.route,
            AppRoute.Profile.route,
            AppRoute.PropertyDetails.route
        ),
        currentRoute: String
    ): Boolean {
        if (appState.sessionMode.name == "GUEST" && gatedRoutes.contains(currentRoute)) {
            navController.navigate(AppRoute.SignInPromptSheet.route)
            return false
        }
        return true
    }

    fun logout(navController: NavController, appState: AppState) {
        navController.navigate(AppRoute.RoleSelection.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }
}
