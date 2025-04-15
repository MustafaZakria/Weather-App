package com.vodafone.home.domain

import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.WeatherRepository
import com.vodafone.home.domain.util.RecentCityError
import com.zek.cryptotracker.core.domain.util.Error
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import javax.inject.Inject

class GetRecentCityUseCase @Inject constructor(
    val cityRepository: CityRepository,
    val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<Weather, Error> {
        return when (val cityIdResult = cityRepository.getSavedCityId()) {
            -1 -> Result.Error(RecentCityError.NO_RECENT_CITY)
            else -> {
                val city = cityRepository.getCityById(cityIdResult)

//                weatherRepository.getWeatherByCoordinates(
//                    city.lat,
//                    city.lon
//                )
                Result.Error(NetworkError.UNKNOWN)
            }
        }

    }
}
