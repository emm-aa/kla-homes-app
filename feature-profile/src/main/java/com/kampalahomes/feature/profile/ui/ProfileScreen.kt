package com.kampalahomes.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.ui.AppState
import com.kampalahomes.core.ui.AppStateViewModel
import com.kampalahomes.core.ui.SessionMode

private const val STITCH_PROFILE_SCREEN_ID = "65b7049bb6964baf91755086c71ca0d2"

@Composable
fun ProfileScreen(
    rootNavController: NavController,
    appState: AppState,
    appStateViewModel: AppStateViewModel
) {
    if (appState.sessionMode == SessionMode.GUEST) {
        GuestProfilePrompt(
            onCreateAccount = {
                rootNavController.navigate(AppRoute.Registration.route)
            },
            onLogin = {
                rootNavController.navigate(AppRoute.Auth.route)
            }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        ProfileHeader()

        Spacer(modifier = Modifier.height(20.dp))

        StatsRow()

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            MenuItem("My Preferences")
            HorizontalDivider()
            MenuItem("Saved Searches") {
                rootNavController.navigate(AppRoute.SavedSearches.route)
            }
            HorizontalDivider()
            MenuItem("Notifications") {
                rootNavController.navigate(AppRoute.Notifications.route)
            }
            HorizontalDivider()
            MenuItem("Help & Support") {
                rootNavController.navigate(AppRoute.HelpSupport.route)
            }
            HorizontalDivider()
            MenuItem("Privacy Policy") {
                rootNavController.navigate(AppRoute.PrivacyPolicy.route)
            }
            HorizontalDivider()
            MenuItem("Log Out") {
                appStateViewModel.updateSessionMode(SessionMode.GUEST)
                appStateViewModel.setLocationPermission(false)
                rootNavController.navigate(AppRoute.Auth.route) {
                    popUpTo("main_flow") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Stitch ref: $STITCH_PROFILE_SCREEN_ID",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
        )
    }
}

@Composable
private fun ProfileHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Card(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "👤")
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(
                text = "Sarah Namatovu",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "sarah@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Member since Mar 2026",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard("12", "Saved")
        StatCard("4", "Enquiries")
        StatCard("2", "Views")
    }
}

@Composable
private fun RowScope.StatCard(value: String, label: String) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun MenuItem(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        if (onClick != null) {
            Text(
                text = ">",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun GuestProfilePrompt(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🔒", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Sign in to access your profile",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Save listings, manage preferences, and contact landlords.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = onCreateAccount,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
    }
}
