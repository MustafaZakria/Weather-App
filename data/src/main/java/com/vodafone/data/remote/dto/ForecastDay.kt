package com.vodafone.data.remote.dto

data class ForecastDay(
    val date_epoch: Int,
    val day: DayDto,
    val hour: List<HourDto>
)