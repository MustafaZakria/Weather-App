package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailDto(
    val current: CurrentDto,
    val forecast: Forecast,
    val location: LocationDto
)