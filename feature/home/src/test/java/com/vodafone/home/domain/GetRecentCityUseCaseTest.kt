package com.vodafone.home.domain

import com.vodafone.core.domain.model.City
import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.WeatherRepository
import com.vodafone.home.domain.util.RecentCityError
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetRecentCityUseCaseTest {

    private val mockCityRepository = mockk<CityRepository>()
    private val mockWeatherRepository = mockk<WeatherRepository>()

    private val testCityId = 1
    private val testCity = City(
        id = testCityId,
        name = "London",
        country = "test",
        lat = 51.5074,
        lon = -0.1278
    )
    private val testWeather = Weather(
        city = "London",
        lat = 51.5074,
        lon = -0.1278,
        temperature = "test",
        icon = "test",
        date = "test",
        condition = "test"
    )

    private lateinit var useCase: GetRecentCityUseCase

    @Before
    fun setup() {
        useCase = GetRecentCityUseCase(mockCityRepository, mockWeatherRepository)
    }

    // Test cases
    @Test
    fun `invoke should return error when no recent city exists`() = runTest {
        // Given
        every { mockCityRepository.getSavedCityId() } returns -1

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(RecentCityError.NO_RECENT_CITY, (result as Result.Error).error)
        coVerify(exactly = 0) { mockCityRepository.getCityById(any()) }
    }

    @Test
    fun `invoke should return weather when recent city exists`() = runTest {
        // Given
        every { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } returns testCity
        coEvery { mockWeatherRepository.getCurrentWeather(testCity.lat, testCity.lon) } returns
                Result.Success(testWeather)

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(testWeather, (result as Result.Success).data)
        coVerify(exactly = 1) { mockCityRepository.getCityById(testCityId) }
        coVerify(exactly = 1) {
            mockWeatherRepository.getCurrentWeather(testCity.lat, testCity.lon)
        }
    }

    @Test
    fun `invoke should return error when city lookup fails`() = runTest {
        // Given
        every { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } throws
                NoSuchElementException("City not found")

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is RecentCityError)
    }

    @Test
    fun `invoke should return error when weather fetch fails`() = runTest {
        // Given
        every { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } returns testCity
        coEvery { mockWeatherRepository.getCurrentWeather(any(), any()) } returns
                Result.Error(NetworkError.SERVER_ERROR)

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).error is NetworkError)
    }

    @Test
    fun `invoke should call repositories in correct sequence`() = runTest {
        // Given
        every { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } returns testCity
        coEvery { mockWeatherRepository.getCurrentWeather(any(), any()) } returns
                Result.Success(testWeather)

        // When
        useCase()

        // Then
        coVerifySequence {
            mockCityRepository.getSavedCityId()
            mockCityRepository.getCityById(testCityId)
            mockWeatherRepository.getCurrentWeather(testCity.lat, testCity.lon)
        }
    }

    @Test
    fun `invoke should handle edge case coordinates`() = runTest {
        // Test cases
        val testCases = listOf(
            Pair(90.0, 180.0),   // Max valid
            Pair(-90.0, -180.0), // Min valid
            Pair(0.0, 0.0)       // Prime meridian/equator
        )

        testCases.forEach { (lat, lon) ->
            // Setup
            val edgeCity = testCity.copy(lat = lat, lon = lon)
            every { mockCityRepository.getSavedCityId() } returns testCityId
            coEvery { mockCityRepository.getCityById(testCityId) } returns edgeCity
            coEvery { mockWeatherRepository.getCurrentWeather(lat, lon) } returns
                    Result.Success(testWeather)

            // When
            val result = useCase()

            // Then
            assertTrue(result is Result.Success)
            coVerify { mockWeatherRepository.getCurrentWeather(lat, lon) }

            // Reset mocks for next iteration
            clearMocks(mockCityRepository, mockWeatherRepository)
        }
    }
}