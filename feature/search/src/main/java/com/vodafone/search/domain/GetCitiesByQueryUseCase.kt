package com.vodafone.search.domain

import com.vodafone.core.domain.model.City
import com.vodafone.data.repository.CityRepository
import javax.inject.Inject

class GetCitiesByQueryUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(query: String): List<City> {
        val allCities = cityRepository.getAllCities()

        return if (query.isBlank()) {
            allCities
        } else {
            //since the data is not large, I filtered data here
            allCities.filter { city ->
                city.name.contains(query, ignoreCase = true)
            }
        }
    }
}