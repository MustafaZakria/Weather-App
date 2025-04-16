package com.vodafone.search.domain

import com.vodafone.core.domain.model.City
import com.vodafone.data.repository.CityRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateRecentCityUseCaseTest {
    // Mock
    private val mockCityRepository = mockk<CityRepository>()

    // Subject under test
    private lateinit var useCase: UpdateRecentCityUseCase

    // Test data
    private val testCity = City(
        id = 1,
        name = "London",
        country = "UK",
        lon = -0.1278,
        lat = 51.5074
    )

    @Before
    fun setup() {
        useCase = UpdateRecentCityUseCase(mockCityRepository)
        coEvery { mockCityRepository.saveCityId(any()) } just Runs
    }

    @Test
    fun `should call saveCityId with correct city ID`() = runTest {
        // When
        useCase.invoke(testCity)

        // Then
        coVerify { mockCityRepository.saveCityId(testCity.id) }
    }
}
