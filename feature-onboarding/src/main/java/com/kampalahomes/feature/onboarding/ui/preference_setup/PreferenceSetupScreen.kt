package com.kampalahomes.feature.onboarding.ui.preference_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kampalahomes.core.model.navigation.AppRoute

@Composable
fun PreferenceSetupScreen(
    navController: NavController
) {
    var minBudget by remember { mutableStateOf(200000f) }
    var maxBudget by remember { mutableStateOf(5000000f) }
    var selectedPropertyTypes by remember { mutableStateOf(setOf<String>()) }
    var preferredAreas by remember { mutableStateOf(setOf<String>()) }

    val propertyTypes = listOf("Apartment", "Studio", "House", "Room")
    val suggestedAreas = listOf("Kololo", "Nakasero", "Ntinda", "Bugolobi", "Makindye", "Kisaasi")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "What are you looking for?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Help us find properties that match your preferences",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(text = "Budget Range (UGX)")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Min: ${formatCurrency(minBudget.toLong())} - Max: ${formatCurrency(maxBudget.toLong())}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        RangeSlider(
            value = minBudget..maxBudget,
            onValueChange = { range ->
                minBudget = range.start
                maxBudget = range.endInclusive
            },
            valueRange = 200000f..5000000f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(text = "Property Type")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(propertyTypes) { type ->
                PropertyTypeChip(
                    type = type,
                    selected = selectedPropertyTypes.contains(type),
                    onClick = {
                        selectedPropertyTypes = if (selectedPropertyTypes.contains(type)) {
                            selectedPropertyTypes - type
                        } else {
                            selectedPropertyTypes + type
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        SectionTitle(text = "Preferred Areas")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(suggestedAreas) { area ->
                AreaChip(
                    area = area,
                    selected = preferredAreas.contains(area),
                    onClick = {
                        preferredAreas = if (preferredAreas.contains(area)) {
                            preferredAreas - area
                        } else {
                            preferredAreas + area
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate(AppRoute.LocationAccess.route) {
                    popUpTo(AppRoute.Splash.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Find Properties",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun PropertyTypeChip(
    type: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(type) },
        modifier = Modifier.height(40.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            labelColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun AreaChip(
    area: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(area) },
        modifier = Modifier.height(40.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
            labelColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    )
}

fun formatCurrency(amount: Long): String {
    return "%,d".format(amount)
}
