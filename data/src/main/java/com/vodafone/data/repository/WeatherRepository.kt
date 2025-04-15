package com.vodafone.data.repository

import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherDetail
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<Weather, NetworkError>
    suspend fun getForecast(lat: Double, lon: Double): Result<WeatherDetail, NetworkError>
}