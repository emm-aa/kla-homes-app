package com.kampalahomes.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kampalahomes.core.model.navigation.AppRoute
import com.kampalahomes.core.model.property.Property

@Preview
@Composable
fun HomeScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            onNotificationsClick = {
                navController.navigate(AppRoute.Notifications.route)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                if (searchQuery.isNotEmpty()) {
                    navController.navigate("${AppRoute.SearchResults.route}?query=$searchQuery")
                }
            },
            onFilterClick = {
                navController.navigate(AppRoute.AdvancedFilters.route)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        FilterChipsRow(
            selectedFilter = "All",
            onFilterSelected = { }
        )

        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader(
            title = "Featured in Kololo",
            onViewAllClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalPropertyList(
            properties = mockProperties.filter { it.neighborhood == "Kololo" },
            onPropertyClick = {
                navController.navigate("${AppRoute.PropertyDetails.route}?propertyId=${it.id}")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))
        SectionHeader(
            title = "Browse by Neighborhood",
            onViewAllClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(12.dp))
        NeighborhoodGrid(
            neighborhoods = listOf("Kololo", "Nakasero", "Ntinda", "Bugolobi"),
            onNeighborhoodClick = { neighborhood ->
                navController.navigate("${AppRoute.SearchResults.route}?neighborhood=$neighborhood")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader(
            title = "Nearby Listings",
            onViewAllClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.height(12.dp))
        PropertyList(
            properties = mockProperties,
            onPropertyClick = {
                navController.navigate("${AppRoute.PropertyDetails.route}?propertyId=${it.id}")
            }
        )

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun TopBar(
    onNotificationsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏠",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kampala Homes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Row {
            IconButton(onClick = onNotificationsClick) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            IconButton(onClick = { /* Language */ }) {
                Text(
                    text = "EN",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search properties...") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = onFilterClick) {
                    Text(
                        text = "🔍",
                        fontSize = 20.sp
                    )
                }
            },
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun FilterChipsRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("All", "Apartment", "Studio", "House", "Room")
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                selected = filter == selectedFilter,
                modifier = Modifier.height(36.dp)
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onViewAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        TextButton(onClick = onViewAllClick) {
            Text(
                text = "View all",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun HorizontalPropertyList(
    properties: List<Property>,
    onPropertyClick: (Property) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(properties) { property ->
            PropertyCard(
                property = property,
                onClick = { onPropertyClick(property) },
                modifier = Modifier.width(240.dp)
            )
        }
    }
}

@Composable
fun PropertyList(
    properties: List<Property>,
    onPropertyClick: (Property) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        properties.forEach { property ->
            PropertyCard(
                property = property,
                onClick = { onPropertyClick(property) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PropertyCard(
    property: Property,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        onClick = onClick
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.LightGray)
            ) {
                Text(
                    text = "📷",
                    fontSize = 48.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${formatCurrency(property.priceUgx)}/month",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${property.bedrooms} bed • ${property.bathrooms} bath",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = property.neighborhood,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun NeighborhoodGrid(
    neighborhoods: List<String>,
    onNeighborhoodClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(neighborhoods.size) { index ->
            val neighborhood = neighborhoods[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                onClick = { onNeighborhoodClick(neighborhood) }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = neighborhood,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

fun formatCurrency(amount: Long): String {
    return "%,d UGX".format(amount)
}

val mockProperties = listOf(
    Property(
        id = "1",
        title = "Modern Apartment in Kololo",
        priceUgx = 2500000,
        bedrooms = 3,
        bathrooms = 2,
        sizeSqft = 1500,
        neighborhood = "Kololo",
        propertyType = "Apartment",
        amenities = listOf("Parking", "Security", "Water 24/7"),
        images = listOf("https://example.com/image1.jpg"),
        landlord = "John Doe"
    ),
    Property(
        id = "2",
        title = "Cozy Studio in Bugolobi",
        priceUgx = 1200000,
        bedrooms = 1,
        bathrooms = 1,
        sizeSqft = 600,
        neighborhood = "Bugolobi",
        propertyType = "Studio",
        amenities = listOf("Close to Transport"),
        images = listOf("https://example.com/image2.jpg"),
        landlord = "Jane Smith"
    ),
    Property(
        id = "3",
        title = "Luxury House in Nakasero",
        priceUgx = 4500000,
        bedrooms = 4,
        bathrooms = 3,
        sizeSqft = 2500,
        neighborhood = "Nakasero",
        propertyType = "House",
        amenities = listOf("Garden", "Parking", "Security"),
        images = listOf("https://example.com/image3.jpg"),
        landlord = "Bob Johnson"
    )
)
