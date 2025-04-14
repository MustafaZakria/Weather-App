package com.vodafone.core.domain.model

data class Weather(
    val city: String,
    val temperature: String,
    val icon: String?,
    val date: String,
    val condition: String
)
