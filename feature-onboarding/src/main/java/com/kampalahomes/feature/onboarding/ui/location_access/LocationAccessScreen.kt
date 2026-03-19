package com.kampalahomes.feature.onboarding.ui.location_access

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kampalahomes.core.ui.AppStateViewModel

@Composable
fun LocationAccessScreen(
    navController: NavController,
    appStateViewModel: AppStateViewModel
) {
    val navigateToMainFlow: () -> Unit = {
        navController.navigate("main_flow") {
            // Clear onboarding + startup stack so back from main exits app.
            popUpTo("startup") { inclusive = true }
            launchSingleTop = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            val isGranted = fineLocationGranted || coarseLocationGranted
            appStateViewModel.setLocationPermission(isGranted)
            appStateViewModel.setFirstRunCompleted(true)
            navigateToMainFlow()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📍",
                fontSize = 64.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Find homes right where you are",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Get location-based recommendations for properties in Kampala, Nakasero, Kololo, Ntinda, Bugolobi, Makindye, and more!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Allow Location",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                appStateViewModel.setLocationPermission(false)
                appStateViewModel.setFirstRunCompleted(true)
                navigateToMainFlow()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Not now",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
