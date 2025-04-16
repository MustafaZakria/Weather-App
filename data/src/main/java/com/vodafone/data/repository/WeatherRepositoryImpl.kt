package com.vodafone.data.repository

import android.util.Log
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.core.util.DispatcherProvider
import com.vodafone.data.remote.WeatherApi
import com.vodafone.data.remote.dto.WeatherDetailDto
import com.vodafone.data.remote.dto.WeatherDto
import com.vodafone.data.remote.mapper.toWeather
import com.vodafone.data.remote.mapper.toWeatherDetail
import com.zek.cryptotracker.core.data.networking.safeCall
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import com.zek.cryptotracker.core.domain.util.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dispatcherProvider: DispatcherProvider
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Result<Weather, NetworkError> {
        return safeCall<WeatherDto> {
            withContext(dispatcherProvider.io) {
                weatherApi.getCurrentWeather("$lat,$lon")
            }
        }.map {
            it.toWeather()
        }
    }

    override suspend fun getForecast(
        lat: Double,
        lon: Double
    ): Result<WeatherDetail, NetworkError> {
        return safeCall<WeatherDetailDto> {
            withContext(dispatcherProvider.io) {
                val request = weatherApi.getForecast("$lat,$lon")
                Log.d("WeatherRepositoryImpl", "$request")
                request
            }
        }.map {
            Log.d("WeatherRepositoryImpl", "$it")
            it.toWeatherDetail()
        }
    }
}