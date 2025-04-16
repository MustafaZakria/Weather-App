package com.vodafone.data.repository

import com.vodafone.core.domain.model.City
import com.vodafone.core.util.DispatcherProvider
import com.vodafone.data.local.CityDao
import com.vodafone.data.local.model.CityEntity
import com.vodafone.data.local.sharedpref.CitySharedPreference
import com.vodafone.data.local.util.CityJsonLoader
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CityRepositoryImplTest {
    // Mocks
    private val mockCityDao = mockk<CityDao>()
    private val mockDispatcherProvider = mockk<DispatcherProvider>()
    private val mockCityJsonLoader = mockk<CityJsonLoader>()
    private val mockCitySharedPreference = mockk<CitySharedPreference>()

    // Test data
    private val testCityEntity =
        CityEntity(id = 1, name = "London", country = "UK", lat = 51.5074, lon = -0.1278)
    private val testCity =
        City(id = 1, name = "London", country = "UK", lat = 51.5074, lon = -0.1278)
    private val testCities = listOf(testCityEntity)

    // Subject
    private lateinit var repository: CityRepositoryImpl

    @Before
    fun setup() {
        coEvery { mockDispatcherProvider.io } returns Dispatchers.Unconfined

        repository = CityRepositoryImpl(
            mockCityDao,
            mockDispatcherProvider,
            mockCityJsonLoader,
            mockCitySharedPreference
        )
    }

    @Test
    fun `loadCities should load from JSON when DB is empty`() = runTest {
        // Given
        coEvery { mockCityDao.getAllCities() } returns emptyList()
        coEvery { mockCityJsonLoader.parseCityJson() } returns testCities
        coEvery { mockCityDao.insertCities(testCities) } just Runs

        // When
        repository.loadCities()

        // Then
        coVerifyOrder {
            mockCityDao.getAllCities()
            mockCityJsonLoader.parseCityJson()
            mockCityDao.insertCities(testCities)
        }
    }

    @Test
    fun `loadCities should skip loading when DB has data`() = runTest {
        // Given
        coEvery { mockCityDao.getAllCities() } returns testCities

        // When
        repository.loadCities()

        // Then
        coVerify(exactly = 0) { mockCityJsonLoader.parseCityJson() }
        coVerify(exactly = 0) { mockCityDao.insertCities(any()) }
    }

    @Test
    fun `getCityById should return mapped city`() = runTest {
        // Given
        coEvery { mockCityDao.getCityById(1) } returns testCityEntity

        // When
        val result = repository.getCityById(1)

        // Then
        assertEquals(testCity, result)
    }

    @Test
    fun `getAllCities should return mapped cities`() = runTest {
        // Given
        coEvery { mockCityDao.getAllCities() } returns testCities

        // When
        val result = repository.getAllCities()

        // Then
        assertEquals(listOf(testCity), result)
    }

    @Test
    fun `saveCityId should delegate to shared preferences`() {
        // Given
        every { mockCitySharedPreference.saveCityId(1) } just Runs

        // When
        repository.saveCityId(1)

        // Then
        verify { mockCitySharedPreference.saveCityId(1) }
    }

    @Test
    fun `getSavedCityId should return ID when available`() {
        // Given
        every { mockCitySharedPreference.getCityId() } returns 1

        // When
        val result = repository.getSavedCityId()

        // Then
        assertEquals(1, result)
    }

    @Test
    fun `getSavedCityId should throw when no ID saved`() {
        // Given
        every { mockCitySharedPreference.getCityId() } returns -1

        // When/Then
        assertThrows(NoSavedCityException::class.java) {
            repository.getSavedCityId()
        }
    }
}