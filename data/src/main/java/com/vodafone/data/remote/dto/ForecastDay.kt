package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDay(
    val date_epoch: Int,
    val day: DayDto,
    val hour: List<HourDto>
)