package com.vodafone.data.repository

import com.vodafone.core.domain.model.City

interface CityRepository {
    suspend fun loadCities()
    suspend fun getAllCities(): List<City>
    suspend fun getCityById(cityId: Int): City
    fun saveCityId(id: Int)
    fun getSavedCityId(): Int
}