package com.vodafone.data.remote

import com.vodafone.data.remote.dto.WeatherDetailDto
import com.vodafone.data.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.weatherapi.com/v1/"
const val QUERY_API_KEY = "key"
const val FORECAST_DAYS = 7

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") location: String //latitude,longitude
    ): Response<WeatherDto>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int = FORECAST_DAYS
    ): Response<WeatherDetailDto>
}