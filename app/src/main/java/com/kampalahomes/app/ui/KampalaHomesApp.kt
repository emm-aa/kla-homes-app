package com.kampalahomes.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kampalahomes.app.ui.navigation.graph.RootNavGraph
import com.kampalahomes.app.ui.theme.KampalaHomesTheme
import com.kampalahomes.core.ui.AppStateViewModel
import com.kampalahomes.core.data.repository.PropertyRepository
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepositoryProvider {
    fun propertyRepository(): PropertyRepository
}

@Composable
fun KampalaHomesApp(
    appStateViewModel: AppStateViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val propertyRepository = EntryPointAccessors
        .fromApplication<RepositoryProvider>(context.applicationContext)
        .propertyRepository()

    KampalaHomesTheme {
        RootNavGraph(
            navController = navController,
            appStateViewModel = appStateViewModel,
            propertyRepository = propertyRepository
        )
    }
}
