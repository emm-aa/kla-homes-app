package com.kampalahomes.app.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.kampalahomes.core.ui.AppStateViewModel
import com.kampalahomes.app.ui.navigation.startupGraph
import com.kampalahomes.app.ui.navigation.onboardingGraph
import com.kampalahomes.app.ui.navigation.mainGraph
import com.kampalahomes.core.data.repository.PropertyRepository

@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appStateViewModel: AppStateViewModel,
    propertyRepository: PropertyRepository
) {
    NavHost(
        navController = navController,
        startDestination = "root",
        modifier = modifier
    ) {
        rootGraph(
            appStateViewModel = appStateViewModel,
            navController = navController,
            propertyRepository = propertyRepository
        )
    }
}

fun NavGraphBuilder.rootGraph(
    appStateViewModel: AppStateViewModel,
    navController: NavHostController,
    propertyRepository: PropertyRepository
) {
    navigation(
        startDestination = "startup",
        route = "root"
    ) {
        startupGraph(
            navController = navController,
            appStateViewModel = appStateViewModel
        )
    }

    onboardingGraph(
        navController = navController,
        appStateViewModel = appStateViewModel
    )

    mainGraph(
        appStateViewModel = appStateViewModel,
        navController = navController,
        propertyRepository = propertyRepository
    )
}
