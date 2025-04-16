package com.vodafone.data.repository

import com.vodafone.core.util.DispatcherProvider
import com.vodafone.data.remote.WeatherApi
import com.vodafone.data.remote.dto.ConditionDto
import com.vodafone.data.remote.dto.CurrentDto
import com.vodafone.data.remote.dto.Forecast
import com.vodafone.data.remote.dto.LocationDto
import com.vodafone.data.remote.dto.WeatherDetailDto
import com.vodafone.data.remote.dto.WeatherDto
import com.vodafone.data.remote.mapper.toWeather
import com.vodafone.data.remote.mapper.toWeatherDetail
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException


@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {
    // Mocks
    private val mockWeatherApi = mockk<WeatherApi>()
    private val mockDispatcherProvider = mockk<DispatcherProvider>()

    // Test data
    private val testLat = 2.0
    private val testLon = 1.0
    private val testConditionDto = ConditionDto(
        text = "test",
        icon = "test",
        code = 1
    )
    private val testCurrentDto = CurrentDto(
        condition = testConditionDto,
        temp_c = 12.3,
        last_updated = ""
    )
    private val testLocationDto = LocationDto(
        name = "London",
        country = "UK",
        lat = testLat,
        lon = testLon,
        region = "test",
        localtime_epoch = 234
    )
    private val testWeatherDto = WeatherDto(
        location = testLocationDto,
        current = testCurrentDto
    )
    private val testWeatherDetailDto = WeatherDetailDto(
        current = testCurrentDto,
        location = testLocationDto,
        forecast = Forecast(listOf())
    )

    // Subject
    private lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        coEvery { mockDispatcherProvider.io } returns Dispatchers.Unconfined

        repository = WeatherRepositoryImpl(
            mockWeatherApi,
            mockDispatcherProvider
        )
    }

    @Test
    fun `getCurrentWeather should return success when api is successful`() = runTest {
        coEvery { mockWeatherApi.getCurrentWeather(any()) } returns Response.success(testWeatherDto)

        val result = repository.getCurrentWeather(testLon, testLon)
        advanceUntilIdle()

        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, testWeatherDto.toWeather())
        coVerify { mockWeatherApi.getCurrentWeather(any()) }
    }

    @Test
    fun `getCurrentWeather should handle no internet error`() = runTest {
        coEvery { mockWeatherApi.getCurrentWeather(any()) } throws IOException()

        val result = repository.getCurrentWeather(testLon, testLon)
        advanceUntilIdle()

        assertEquals(Result.Error(NetworkError.NO_INTERNET), result)
    }

    @Test
    fun `getCurrentWeather should handle server error`() = runTest {
        // Given
        val errorResponse = Response.error<WeatherDto>(500, "".toResponseBody())
        coEvery { mockWeatherApi.getCurrentWeather(any()) } returns errorResponse

        // When
        val result = repository.getCurrentWeather(testLat, testLon)

        // Then
        assertEquals(Result.Error(NetworkError.SERVER_ERROR), result)
    }

    @Test
    fun `getCurrentWeather should handle timeout error`() = runTest {
        // Given
        val errorResponse = Response.error<WeatherDto>(408, "".toResponseBody())
        coEvery { mockWeatherApi.getCurrentWeather(any()) } returns errorResponse

        // When
        val result = repository.getCurrentWeather(testLat, testLon)

        // Then
        assertEquals(Result.Error(NetworkError.REQUEST_TIMEOUT), result)
    }

    @Test
    fun `getForecast should return success when api is successful`() = runTest {
        coEvery { mockWeatherApi.getForecast(any()) } returns Response.success(testWeatherDetailDto)

        val result = repository.getForecast(testLon, testLon)
        advanceUntilIdle()

        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).data, testWeatherDetailDto.toWeatherDetail())
        coVerify { mockWeatherApi.getForecast(any()) }
    }

    @Test
    fun `both methods should use IO dispatcher`() = runTest {
        // Given
        val testDispatcher = StandardTestDispatcher()
        coEvery { mockDispatcherProvider.io } returns testDispatcher
        coEvery { mockWeatherApi.getCurrentWeather(any()) } returns Response.success(testWeatherDto)
        coEvery { mockWeatherApi.getForecast(any()) } returns Response.success(testWeatherDetailDto)

        // When
        repository.getCurrentWeather(testLat, testLon)
        repository.getForecast(testLat, testLon)

        // Then
        coVerify { mockDispatcherProvider.io }
    }
}