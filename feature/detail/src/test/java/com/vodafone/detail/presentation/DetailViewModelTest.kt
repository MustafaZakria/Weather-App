package com.vodafone.detail.presentation

import androidx.lifecycle.SavedStateHandle
import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.detail.domain.GetWeatherDetailUseCase
import com.vodafone.detail.navigation.LAT_ARG
import com.vodafone.detail.navigation.LON_ARG
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testLat = 51.5074
    private val testLon = -0.1278
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

    private val mockUseCase = mockk<GetWeatherDetailUseCase>()
    private val mockSavedStateHandle = mockk<SavedStateHandle>()

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { mockUseCase(any(), any()) } returns Result.Success(testWeatherDetail)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Test cases
    @Test
    fun `initial state is loading when valid coordinates provided`() = runTest {
        // Given
        every { mockSavedStateHandle.get<String>(LAT_ARG) } returns testLat.toString()
        every { mockSavedStateHandle.get<String>(LON_ARG) } returns testLon.toString()

        // When
        viewModel = DetailViewModel(mockUseCase, mockSavedStateHandle)

        // Then
        assertEquals(true, viewModel.uiState.value.isLoading)
        assertEquals(false, viewModel.uiState.value.isError)
        assertEquals(null, viewModel.uiState.value.detail)
    }

    @Test
    fun `should set error state when coordinates are missing`() = runTest {
        // Given
        every { mockSavedStateHandle.get<String>(LAT_ARG) } returns null
        every { mockSavedStateHandle.get<String>(LON_ARG) } returns null

        // When
        viewModel = DetailViewModel(mockUseCase, mockSavedStateHandle)

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(true, viewModel.uiState.value.isError)
        assertEquals(null, viewModel.uiState.value.detail)
    }

    @Test
    fun `should set error state when latitude is invalid`() = runTest {
        // Given
        every { mockSavedStateHandle.get<String>(LAT_ARG) } returns null
        every { mockSavedStateHandle.get<String>(LON_ARG) } returns testLon.toString()

        // When
        viewModel = DetailViewModel(mockUseCase, mockSavedStateHandle)

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(true, viewModel.uiState.value.isError)
    }

    @Test
    fun `should update state with weather detail on success`() = runTest {
        // Given
        every { mockSavedStateHandle.get<String>(LAT_ARG) } returns testLat.toString()
        every { mockSavedStateHandle.get<String>(LON_ARG) } returns testLon.toString()

        // When
        viewModel = DetailViewModel(mockUseCase, mockSavedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle() // wait for the coroutine to finish

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(false, viewModel.uiState.value.isError)
        assertEquals(testWeatherDetail, viewModel.uiState.value.detail)
        coVerify { mockUseCase(testLat, testLon) }
    }

    @Test
    fun `should set error state when use case fails`() = runTest {
        // Given
        every { mockSavedStateHandle.get<String>(LAT_ARG) } returns testLat.toString()
        every { mockSavedStateHandle.get<String>(LON_ARG) } returns testLon.toString()
        coEvery { mockUseCase(any(), any()) } returns Result.Error(NetworkError.SERVER_ERROR)

        // When
        viewModel = DetailViewModel(mockUseCase, mockSavedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(true, viewModel.uiState.value.isError)
        assertEquals(null, viewModel.uiState.value.detail)
    }
}