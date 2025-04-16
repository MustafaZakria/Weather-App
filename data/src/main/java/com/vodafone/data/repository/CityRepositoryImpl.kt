package com.vodafone.data.repository

import com.vodafone.core.domain.model.City
import com.vodafone.core.util.DispatcherProvider
import com.vodafone.data.local.CityDao
import com.vodafone.data.local.sharedpref.CitySharedPreference
import com.vodafone.data.local.util.CityJsonLoader
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao,
    private val dispatcherProvider: DispatcherProvider,
    private val cityJsonLoader: CityJsonLoader,
    private val citySharedPreference: CitySharedPreference
) : CityRepository {

    override suspend fun loadCities() {
        withContext(dispatcherProvider.io) {
            if (cityDao.getAllCities().isEmpty()) {
                val cities = cityJsonLoader.parseCityJson()
                cityDao.insertCities(cities)
            }
        }
    }

    override suspend fun getAllCities(): List<City> {
        return withContext(dispatcherProvider.io) {
            cityDao.getAllCities().map { it.toCity() }
        }
    }

    override suspend fun getCityById(cityId: Int): City {
        return withContext(dispatcherProvider.io) {
            cityDao.getCityById(cityId).toCity()
        }
    }

    override fun saveCityId(id: Int) {
        citySharedPreference.saveCityId(id)
    }

    override fun getSavedCityId(): Int {
        val id = citySharedPreference.getCityId()

        return if (id != -1) {
            id
        } else {
            throw NoSavedCityException()
        }
    }
}

class NoSavedCityException : Exception()