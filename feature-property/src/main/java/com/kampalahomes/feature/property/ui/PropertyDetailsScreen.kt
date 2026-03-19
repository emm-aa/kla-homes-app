package com.kampalahomes.feature.property.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.model.property.Property
import com.kampalahomes.core.ui.AppState
import com.kampalahomes.core.ui.SessionMode

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PropertyDetailsScreen(
    property: Property,
    navController: NavController,
    appState: AppState,
    onBookmarkToggle: () -> Unit = {}
) {
    var isBookmarked by remember { mutableStateOf(false) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                onBackClick = { navController.navigateUp() },
                onShareClick = { /* TODO: Share functionality */ },
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    if (appState.sessionMode == SessionMode.GUEST) {
                        navController.navigate(AppRoute.SignInPromptSheet.route)
                    } else {
                        isBookmarked = !isBookmarked
                        onBookmarkToggle()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Image/Gallery
            HeroImageSection(imageUrl = property.images.firstOrNull())

            // Property Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Title and Location
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "📍",
                        fontSize = 16.sp
                    )
                    Text(
                        text = property.neighborhood,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Price
                Text(
                    text = "UGX ${formatPrice(property.priceUgx)}/month",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Stats Row
                StatsRow(
                    bedrooms = property.bedrooms,
                    bathrooms = property.bathrooms,
                    sizeSqft = property.sizeSqft
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                DescriptionSection(
                    description = property.description ?: "No description available.",
                    isExpanded = isDescriptionExpanded,
                    onToggle = { isDescriptionExpanded = !isDescriptionExpanded }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Amenities
                AmenitiesSection(amenities = property.amenities)

                Spacer(modifier = Modifier.height(24.dp))

                // Landlord Card
                LandlordCard(landlordName = property.landlord)

                Spacer(modifier = Modifier.height(24.dp))

                // Secondary CTA: Affordability Calculator
                OutlinedButton(
                    onClick = {
                        navController.navigate("${AppRoute.AffordabilityCalculator.route}?rent=${property.priceUgx}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Can I Afford This?")
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Primary CTA: Contact Landlord
                Button(
                    onClick = {
                        if (appState.sessionMode == SessionMode.GUEST) {
                            navController.navigate(AppRoute.SignInPromptSheet.route)
                        } else {
                            navController.navigate(AppRoute.EnquirySentConfirmation.route)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Contact Landlord")
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share property",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (isBookmarked) "Remove from saved" else "Save property",
                    tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun HeroImageSection(imageUrl: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        // TODO: Load actual image with Coil using imageUrl
        Text(
            text = "🏠",
            fontSize = 80.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun StatsRow(bedrooms: Int, bathrooms: Int, sizeSqft: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        StatItem(icon = "🛏️", label = "$bedrooms Beds")
        StatItem(icon = "🚿", label = "$bathrooms Baths")
        StatItem(icon = "📐", label = "$sizeSqft sqft")
    }
}

@Composable
private fun StatItem(icon: String, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = icon, fontSize = 20.sp)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Column {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            maxLines = if (isExpanded) Int.MAX_VALUE else 3
        )

        if (description.length > 150) {
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(onClick = onToggle) {
                Text(
                    text = if (isExpanded) "Show less" else "Read more",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AmenitiesSection(amenities: List<String>) {
    Column {
        Text(
            text = "Amenities",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amenities chips grid
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            amenities.forEach { amenity ->
                AssistChip(
                    onClick = { },
                    label = { Text(amenity) },
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }
    }
}

@Composable
private fun LandlordCard(landlordName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👤", fontSize = 24.sp)
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = landlordName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    // Verification indicator
                    Text(text = "✓", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Verified Landlord",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private fun formatPrice(price: Long): String {
    return String.format(java.util.Locale.US, "%,d", price)
}
