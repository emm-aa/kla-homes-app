package com.kampalahomes.core.model.property

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val id: String,
    val title: String,
    val priceUgx: Long,
    val bedrooms: Int,
    val bathrooms: Int,
    val sizeSqft: Int,
    val neighborhood: String,
    val propertyType: String,
    val amenities: List<String>,
    val images: List<String>,
    val landlord: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String? = null
)