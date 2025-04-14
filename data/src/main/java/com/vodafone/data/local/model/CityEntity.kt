package com.vodafone.data.local.model

import androidx.room.Entity

@Entity(tableName = "cities")
data class CityEntity(
    val id: Int,
    val name: String,
    val country: String,
    val lon: Double,
    val lat: Double
)
