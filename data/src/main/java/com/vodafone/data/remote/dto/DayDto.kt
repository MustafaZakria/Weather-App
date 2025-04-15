package com.vodafone.data.remote.dto

data class DayDto(
    val avgtemp_c: Double,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: ConditionDto,
)