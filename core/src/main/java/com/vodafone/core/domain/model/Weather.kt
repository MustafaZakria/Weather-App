package com.vodafone.core.domain.model

data class Weather(
    val city: String,
    val lat: Double,
    val lon: Double,
    val temperature: String,
    val icon: String?,
    val date: String,
    val condition: String
)
