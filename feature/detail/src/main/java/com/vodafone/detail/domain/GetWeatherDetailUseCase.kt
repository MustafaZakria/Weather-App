package com.vodafone.detail.domain

import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.data.repository.WeatherRepository
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import javax.inject.Inject

class GetWeatherDetailUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Result<WeatherDetail, NetworkError> {
        return weatherRepository.getForecast(lat, lon)
    }
}