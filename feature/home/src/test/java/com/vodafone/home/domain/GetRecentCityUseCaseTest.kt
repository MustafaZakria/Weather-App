package com.vodafone.home.domain

import com.vodafone.core.domain.model.City
import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.NoSavedCityException
import com.vodafone.data.repository.WeatherRepository
import com.vodafone.home.domain.util.RecentCityError
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
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

    @Test
    fun `invoke should return NO_RECENT_CITY when NoSavedCityException is thrown`() = runTest {
        // Given
        coEvery { mockCityRepository.getSavedCityId() } throws NoSavedCityException()

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(RecentCityError.NO_RECENT_CITY, (result as Result.Error).error)
        coVerify(exactly = 0) { mockCityRepository.getCityById(any()) }
        coVerify(exactly = 0) { mockWeatherRepository.getCurrentWeather(any(), any()) }
    }

    @Test
    fun `invoke should return weather when all operations succeed`() = runTest {
        // Given
        coEvery { mockCityRepository.getSavedCityId() } returns testCityId
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
    fun `invoke should return FETCHING_CITY_FAILED when city lookup fails`() = runTest {
        // Given
        coEvery { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } throws
                NoSuchElementException("City not found")

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(RecentCityError.FETCHING_CITY_FAILED, (result as Result.Error).error)
    }

    @Test
    fun `invoke should return weather repository error directly`() = runTest {
        // Given
        val testError = NetworkError.SERVER_ERROR
        coEvery { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } returns testCity
        coEvery { mockWeatherRepository.getCurrentWeather(any(), any()) } returns
                Result.Error(testError)

        // When
        val result = useCase()

        // Then
        assertTrue(result is Result.Error)
        assertEquals(testError, (result as Result.Error).error)
    }

    @Test
    fun `invoke should call repositories in correct sequence`() = runTest {
        // Given
        coEvery { mockCityRepository.getSavedCityId() } returns testCityId
        coEvery { mockCityRepository.getCityById(testCityId) } returns testCity
        coEvery { mockWeatherRepository.getCurrentWeather(any(), any()) } returns
                Result.Success(testWeather)

        // When
        useCase()

        // Then
        coVerifyOrder {
            mockCityRepository.getSavedCityId()
            mockCityRepository.getCityById(testCityId)
            mockWeatherRepository.getCurrentWeather(testCity.lat, testCity.lon)
        }
    }
}