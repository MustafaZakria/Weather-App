package com.vodafone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    val condition: ConditionDto,
    val temp_c: Double,
    val last_updated: String
)