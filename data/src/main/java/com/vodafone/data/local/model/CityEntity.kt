package com.vodafone.data.local.model

import androidx.room.Entity
import com.vodafone.core.domain.model.City

@Entity(tableName = "cities")
data class CityEntity(
    val id: Int,
    val name: String,
    val country: String,
    val lon: Double,
    val lat: Double
) {
    fun toCity(): City {
        return City(
            id = id,
            name = name,
            country = country,
            lon = lon,
            lat = lat
        )
    }
}
