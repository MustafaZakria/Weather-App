package com.vodafone.data.remote.dto

data class WeatherDetailDto(
    val current: CurrentDto,
    val forecast: Forecast,
    val location: LocationDto
)