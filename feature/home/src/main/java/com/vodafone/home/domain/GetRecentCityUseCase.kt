package com.vodafone.home.domain

import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.NoSavedCityException
import com.vodafone.data.repository.WeatherRepository
import com.vodafone.home.domain.util.RecentCityError
import com.zek.cryptotracker.core.domain.util.Error
import com.zek.cryptotracker.core.domain.util.Result
import javax.inject.Inject

class GetRecentCityUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<Weather, Error> {
//        val cityIdResult = cityRepository.getSavedCityId()
//
//        return if (cityIdResult == -1) {
//            Result.Error(RecentCityError.NO_RECENT_CITY)
//        } else {
//            val city = cityRepository.getCityById(cityIdResult)
//
//            weatherRepository.getCurrentWeather(
//                city.lat,
//                city.lon
//            )
//        }
        return try {
            val cityIdResult = cityRepository.getSavedCityId()  // This will throw an exception if no city is saved

            val city = cityRepository.getCityById(cityIdResult) // This will throw an exception if the city is not found

            weatherRepository.getCurrentWeather(
                city.lat,
                city.lon
            )   // This will return the result of the weather fetch directly from the repository

        } catch (e: NoSavedCityException) {
            Result.Error(RecentCityError.NO_RECENT_CITY)
        } catch (e: Exception) {
            Result.Error(RecentCityError.FETCHING_CITY_FAILED)
        }
    }
}
