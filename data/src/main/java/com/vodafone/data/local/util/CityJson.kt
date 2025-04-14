package com.vodafone.data.local.util

import com.vodafone.data.local.model.CityEntity

data class CityJson(
    val id: Int,
    val name: String,
    val country: String,
    val lon: Double,
    val lat: Double
) {
    fun toCityEntity(): CityEntity {
        return CityEntity(
            id = id,
            name = name,
            country = country,
            lon = lon,
            lat = lat
        )
    }
}
