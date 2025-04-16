package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HourDto(
    val condition: ConditionDto,
    val temp_c: Double,
    val time: String,
)