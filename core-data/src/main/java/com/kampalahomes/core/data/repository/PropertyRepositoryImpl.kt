package com.kampalahomes.core.data.repository

import com.kampalahomes.core.model.property.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepositoryImpl @Inject constructor() : PropertyRepository {
    private val _savedProperties = MutableStateFlow<Set<String>>(emptySet())
    private val savedProperties = _savedProperties.asStateFlow()

    override suspend fun getProperties(): List<Property> {
        return mockProperties
    }

    override suspend fun getPropertyById(id: String): Property? {
        return mockProperties.find { it.id == id }
    }

    override suspend fun getSavedProperties(): List<Property> {
        return mockProperties.filter { it.id in _savedProperties.value }
    }

    override suspend fun saveProperty(propertyId: String) {
        _savedProperties.value = _savedProperties.value + propertyId
    }

    override suspend fun unsaveProperty(propertyId: String) {
        _savedProperties.value = _savedProperties.value - propertyId
    }

    override suspend fun searchProperties(
        query: String?,
        minPrice: Long?,
        maxPrice: Long?,
        bedrooms: Int?,
        propertyType: String?
    ): List<Property> {
        return mockProperties.filter { property ->
            (query == null || property.title.contains(query, ignoreCase = true)) &&
            (minPrice == null || property.priceUgx >= minPrice) &&
            (maxPrice == null || property.priceUgx <= maxPrice) &&
            (bedrooms == null || property.bedrooms >= bedrooms) &&
            (propertyType == null || property.propertyType == propertyType)
        }
    }

    companion object {
        private val mockProperties = listOf(
            Property(
                id = "1",
                title = "Modern Apartment in Kololo",
                priceUgx = 2500000,
                bedrooms = 3,
                bathrooms = 2,
                sizeSqft = 1500,
                neighborhood = "Kololo",
                amenities = listOf("Parking", "Security", "Water 24/7", "Generator", "Gym", "Swimming Pool"),
                images = listOf("https://example.com/image1.jpg"),
                landlord = "John Doe",
                latitude = 0.315,
                longitude = 32.592,
                propertyType = "Apartment",
                description = "Spacious 3-bedroom apartment in the heart of Kololo. Features modern finishes, ample natural light, and stunning city views. The property includes a master bedroom with en-suite bathroom, a fully equipped kitchen, and a comfortable living area. Located in a secure compound with 24/7 security, backup water supply, and standby generator."
            ),
            Property(
                id = "2",
                title = "Cozy Studio in Bugolobi",
                priceUgx = 1200000,
                bedrooms = 1,
                bathrooms = 1,
                sizeSqft = 600,
                neighborhood = "Bugolobi",
                amenities = listOf("Close to Transport", "Water", "Security"),
                images = listOf("https://example.com/image2.jpg"),
                landlord = "Jane Smith",
                latitude = 0.321,
                longitude = 32.610,
                propertyType = "Studio",
                description = "Affordable studio apartment perfect for young professionals. Compact yet functional space with a kitchenette and private bathroom. Conveniently located near public transport routes and close to Bugolobi shopping areas."
            ),
            Property(
                id = "3",
                title = "Luxury Villa in Nakasero",
                priceUgx = 4800000,
                bedrooms = 4,
                bathrooms = 3,
                sizeSqft = 2800,
                neighborhood = "Nakasero",
                amenities = listOf("Parking", "Security", "Garden", "Generator", "Servant Quarters", "DSTV"),
                images = listOf("https://example.com/image3.jpg"),
                landlord = "David Musoke",
                latitude = 0.325,
                longitude = 32.575,
                propertyType = "House",
                description = "Stunning 4-bedroom villa in prestigious Nakasero. Beautifully landscaped garden, modern kitchen with granite countertops, spacious living and dining areas. Features include a master suite with walk-in closet, three guest bedrooms, and servant quarters. Gated compound with ample parking and 24/7 security."
            )
        )
    }
}