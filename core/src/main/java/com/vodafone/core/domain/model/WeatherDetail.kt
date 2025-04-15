package com.vodafone.core.domain.model

data class WeatherDetail(
    val cityName: String,
    val temperature: String,
    val icon: String,
    val date: String,
    val status: String,
    val dailyForecast: List<Daily>,
//    val hourlyForecast: List<Hourly>
)

data class Daily(
    val avgTemperature: String,
    val minTemperature: String,
    val maxTemperature: String,
    val icon: String,
    val date: String,
    val status: String
)

data class Hourly(
    val hour: String,
    val temperature: String,
    val icon: String
)
