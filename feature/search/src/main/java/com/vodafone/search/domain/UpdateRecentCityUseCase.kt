package com.vodafone.search.domain

import android.util.Log
import com.vodafone.core.domain.model.City
import com.vodafone.data.repository.CityRepository
import javax.inject.Inject

class UpdateRecentCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    operator fun invoke(city: City) {
        cityRepository.saveCityId(city.id)
    }
}