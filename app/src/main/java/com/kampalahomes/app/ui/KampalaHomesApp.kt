package com.kampalahomes.app.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kampalahomes.app.ui.navigation.graph.RootNavGraph
import com.kampalahomes.app.ui.theme.KampalaHomesTheme
import com.kampalahomes.core.ui.AppStateViewModel

@Composable
fun KampalaHomesApp(
    appStateViewModel: AppStateViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    KampalaHomesTheme {
        RootNavGraph(
            navController = navController,
            appStateViewModel = appStateViewModel
        )
    }
}
