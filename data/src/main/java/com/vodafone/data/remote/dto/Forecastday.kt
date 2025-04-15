package com.vodafone.data.remote.dto

data class Forecastday(
    val date: String,
    val day: DayDto,
    val hour: List<HourDto>
)