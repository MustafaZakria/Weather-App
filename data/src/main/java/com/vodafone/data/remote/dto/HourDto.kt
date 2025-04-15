package com.vodafone.data.remote.dto

data class HourDto(
    val conditionDto: ConditionDto,
    val temp_c: Double,
    val time: String,
)