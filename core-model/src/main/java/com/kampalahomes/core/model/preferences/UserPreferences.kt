package com.kampalahomes.core.model.preferences

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val budgetMin: Long? = null,
    val budgetMax: Long? = null,
    val propertyTypes: List<String> = emptyList(),
    val preferredAreas: List<String> = emptyList(),
    val minBedrooms: Int? = null,
    val minBathrooms: Int? = null,
    val hasParking: Boolean? = null,
    val hasFurnishing: Boolean? = null
)