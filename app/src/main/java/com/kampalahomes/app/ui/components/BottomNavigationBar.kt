package com.kampalahomes.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.ui.AppState

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    appState: AppState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        BottomNavItem.entries.forEach { bottomNavItem ->
            val isGuestGated = listOf(
                AppRoute.Saved.route,
                AppRoute.Profile.route
            ).contains(bottomNavItem.route)

            val isAccessible = if (isGuestGated && appState.sessionMode.name == "GUEST") {
                false
            } else {
                true
            }

            if (isAccessible) {
                val selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true
                NavigationBarItem(
                    icon = { Icon(bottomNavItem.icon, contentDescription = bottomNavItem.label) },
                    label = { Text(bottomNavItem.label) },
                    selected = selected,
                    onClick = {
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

enum class BottomNavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
) {
    HOME(
        route = AppRoute.Home.route,
        icon = Icons.Default.Home,
        label = "Home"
    ),
    MAP(
        route = AppRoute.Map.route,
        icon = Icons.Default.Place,
        label = "Map"
    ),
    SAVED(
        route = AppRoute.Saved.route,
        icon = Icons.Default.Favorite,
        label = "Saved"
    ),
    PROFILE(
        route = AppRoute.Profile.route,
        icon = Icons.Default.Person,
        label = "Profile"
    )
}
