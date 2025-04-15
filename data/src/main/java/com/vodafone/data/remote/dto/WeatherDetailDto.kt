package com.vodafone.data.remote.dto

data class WeatherDetailDto(
    val currentDto: CurrentDto,
    val forecast: Forecast,
    val location: LocationDto
)