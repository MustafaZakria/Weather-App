package com.vodafone.data.repository

import com.vodafone.core.util.DispatcherProvider
import com.vodafone.data.local.CityDao
import com.vodafone.data.local.model.CityEntity
import com.vodafone.data.local.sharedpref.CitySharedPreference
import com.vodafone.data.local.util.CityJsonLoader
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    val cityDao: CityDao,
    val dispatcherProvider: DispatcherProvider,
    val CityJsonLoader: CityJsonLoader
): CityRepository {

    suspend fun loadCities() {
        withContext(dispatcherProvider.io) {
            val cities = CityJsonLoader.parseCityJson()
            cityDao.insertCities(cities)
        }
    }
    suspend fun getAllCities(): List<CityEntity> {
        return cityDao.getAllCities()
    }
}