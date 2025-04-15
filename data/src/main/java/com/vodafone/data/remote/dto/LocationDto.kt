package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String,
    val country: String,
    val localtime: String,
    val region: String,
    val lat: Double,
    val lon: Double
)
