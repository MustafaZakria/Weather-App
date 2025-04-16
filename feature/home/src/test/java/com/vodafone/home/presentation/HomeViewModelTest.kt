package com.vodafone.home.presentation

import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.home.domain.GetRecentCityUseCase
import com.vodafone.home.domain.util.RecentCityError
import com.zek.cryptotracker.core.domain.util.NetworkError
import com.zek.cryptotracker.core.domain.util.Result
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    // Mocks
    private val mockGetRecentCityUseCase = mockk<GetRecentCityUseCase>()
    private val mockCityRepository = mockk<CityRepository>()

    // Test data
    private val testWeather = Weather(
        city = "London",
        lat = 51.5074,
        lon = -0.1278,
        temperature = "test",
        icon = "test",
        date = "test",
        condition = "test"
    )
    private val testError = NetworkError.SERVER_ERROR

    // Subject under test
    private lateinit var viewModel: HomeViewModel

    // Coroutine testing
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { mockCityRepository.loadCities() } just Runs
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Given
        coEvery { mockGetRecentCityUseCase() } coAnswers {
            delay(1000)
            Result.Success(testWeather)
        }

        // When
        viewModel = HomeViewModel(mockGetRecentCityUseCase, mockCityRepository)

        // Then
        assertTrue(viewModel.recentWeatherState.value is RecentWeatherState.Loading)
    }

    @Test
    fun `should update to Success state when recent city is found`() = runTest {
        // Given
        coEvery { mockGetRecentCityUseCase() } returns Result.Success(testWeather)

        // When
        viewModel = HomeViewModel(mockGetRecentCityUseCase, mockCityRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.recentWeatherState.value as RecentWeatherState.Success
        assertEquals(testWeather, state.weather)
        coVerify { mockCityRepository.loadCities() }
    }

    @Test
    fun `should update to NoRecentCity state when no recent city exists`() = runTest {
        // Given
        coEvery { mockGetRecentCityUseCase() } returns Result.Error(RecentCityError.NO_RECENT_CITY)

        // When
        viewModel = HomeViewModel(mockGetRecentCityUseCase, mockCityRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.recentWeatherState.value is RecentWeatherState.NoRecentCity)
        coVerify(exactly = 0) { viewModel.errorChannel.collect(any()) }
    }

    @Test
    fun `should update to Error state and send error when fetch fails`() = runTest {
        // Given
        val errorText = testError.asUiText()
        coEvery { mockGetRecentCityUseCase() } returns Result.Error(testError)

        // When
        viewModel = HomeViewModel(mockGetRecentCityUseCase, mockCityRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.recentWeatherState.value as RecentWeatherState.Error
        assertEquals(errorText, state.error)

        // Verify error was sent to channel
        val receivedError = viewModel.errorChannel.first()
        assertEquals(errorText, receivedError)
    }

    @Test
    fun `should call loadCities before loading recent city`() = runTest {
        // Given
        coEvery { mockGetRecentCityUseCase() } returns Result.Success(testWeather)

        // When
        viewModel = HomeViewModel(mockGetRecentCityUseCase, mockCityRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerifyOrder {
            mockCityRepository.loadCities()
            mockGetRecentCityUseCase()
        }
    }
}