package com.vodafone.detail.domain

import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.data.repository.WeatherRepository
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetWeatherDetailUseCaseTest {

    private lateinit var useCase: GetWeatherDetailUseCase

    private val mockRepository = mockk<WeatherRepository>()

    private val testWeatherDetail = WeatherDetail(
        cityName = "testCityName",
        lat = 2.0,
        lon = 2.0,
        temperature = "testTemperature",
        icon = "testIcon",
        date = "testDate",
        status = "testStatus",
        dailyForecast = listOf()
    )
    private val testError = NetworkError.SERVER_ERROR
    private val testLat = 51.5074
    private val testLon = -0.1278

    @Before
    fun setUp() {
        useCase = GetWeatherDetailUseCase(mockRepository)
    }

    @Test
    fun `invoke should return success when repository returns data`() = runTest {

        coEvery { mockRepository.getForecast(any(), any()) } returns Result.Success(
            testWeatherDetail
        )

        val result = useCase(testLat, testLon)

        assertTrue(result is Result.Success)
        assertEquals(testWeatherDetail, (result as Result.Success).data)
        coVerify { mockRepository.getForecast(testLat, testLon) }
    }

    @Test
    fun `invoke should return failure when repository returns error`() = runTest {

        coEvery { mockRepository.getForecast(any(), any()) } returns Result.Error(testError)

        val result = useCase(testLat, testLon)

        assertTrue(result is Result.Error)
        assertEquals(testError, (result as Result.Error).error)
    }

    @Test
    fun `invoke should call repository with correct parameters`() = runTest {

        coEvery { mockRepository.getForecast(any(), any()) } returns Result.Success(
            testWeatherDetail
        )

        useCase(testLat, testLon)

        coVerify(exactly = 1) {
            mockRepository.getForecast(
                eq(testLat),
                eq(testLon)
            )
        }
    }

    @Test
    fun `invoke should handle edge case coordinates`() = runTest {

        val edgeCases = listOf(
            Pair(90.0, 180.0),
            Pair(-90.0, -180.0),
            Pair(0.0, 0.0)
        )

        coEvery { mockRepository.getForecast(any(), any()) } returns Result.Success(
            testWeatherDetail
        )

        edgeCases.forEach { (lat, lon) ->

            val result = useCase(lat, lon)

            assertTrue(result is Result.Success)
            coVerify { mockRepository.getForecast(lat, lon) }
        }
    }
}