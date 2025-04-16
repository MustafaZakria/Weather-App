package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val forecastday: List<ForecastDay>
)