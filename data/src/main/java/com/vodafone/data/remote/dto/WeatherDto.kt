package com.vodafone.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    val location: LocationDto,
    val current: CurrentDto
)


