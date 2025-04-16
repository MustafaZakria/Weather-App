package com.vodafone.search.presentation

import com.vodafone.core.domain.model.City
import com.vodafone.search.domain.GetCitiesByQueryUseCase
import com.vodafone.search.domain.UpdateRecentCityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    // Mocks
    private val mockGetCitiesByQueryUseCase = mockk<GetCitiesByQueryUseCase>()
    private val mockUpdateRecentCityUseCase = mockk<UpdateRecentCityUseCase>()

    // Test data
    private val testCities = listOf(
        City(id = 1, name = "London", country = "UK", lat = 51.5074, lon = -0.1278),
        City(id = 2, name = "Paris", country = "France", lat = 48.8566, lon = 2.3522),
    )


    // Subject under test
    private lateinit var viewModel: SearchViewModel

    // Coroutine testing
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading and empty list`() = runTest {
        // Given
        coEvery { mockGetCitiesByQueryUseCase(any()) } coAnswers {
            delay(1000)
            testCities
        }

        // When
        viewModel = SearchViewModel(mockGetCitiesByQueryUseCase, mockUpdateRecentCityUseCase)

        // Then
        assertEquals(viewModel.cityListLoading.value, true)
        assertEquals(viewModel.cityList.value, emptyList<City>())
    }

    @Test
    fun `should update city list and loading false when cities loaded`() = runTest {
        // Given
        coEvery { mockGetCitiesByQueryUseCase.invoke(any()) } returns testCities

        // When
        viewModel = SearchViewModel(mockGetCitiesByQueryUseCase, mockUpdateRecentCityUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(viewModel.cityList.value, testCities)
        assertEquals(viewModel.cityListLoading.value, false)
    }

    @Test
    fun `should update city list when search query changes`() = runTest {
        // Given
        coEvery { mockGetCitiesByQueryUseCase.invoke("lon") } returns testCities.take(1)

        // When
        viewModel = SearchViewModel(mockGetCitiesByQueryUseCase, mockUpdateRecentCityUseCase)
        viewModel.onSearchValueChange("lon")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(testCities.take(1), viewModel.cityList.value)
        assertFalse(viewModel.cityListLoading.value)
        coVerify { mockGetCitiesByQueryUseCase.invoke("lon") }
    }

    @Test
    fun `should update loading state during search operation`() = runTest {
        // Given
        coEvery { mockGetCitiesByQueryUseCase.invoke("lon") } coAnswers {
            delay(500)
            testCities.take(1)
        }

        // When
        viewModel = SearchViewModel(mockGetCitiesByQueryUseCase, mockUpdateRecentCityUseCase)
        viewModel.onSearchValueChange("lon")
        testDispatcher.scheduler.advanceTimeBy(400) //to get over the debouncing

        assertTrue(viewModel.cityListLoading.value)

        testDispatcher.scheduler.advanceTimeBy(500)
        assertFalse(viewModel.cityListLoading.value)
    }
}