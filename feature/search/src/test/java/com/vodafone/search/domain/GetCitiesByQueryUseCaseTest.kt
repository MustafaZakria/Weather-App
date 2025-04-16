package com.vodafone.search.domain

import com.vodafone.core.domain.model.City
import com.vodafone.data.repository.CityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetCitiesByQueryUseCaseTest {
    // Mocks
    private val mockCityRepository = mockk<CityRepository>()

    // Test data
    private val testCities = listOf(
        City(id = 1, name = "London", country = "UK", lat = 51.5074, lon = -0.1278),
        City(id = 2, name = "Paris", country = "France", lat = 48.8566, lon = 2.3522),
    )

    // Subject under test
    private lateinit var useCase: GetCitiesByQueryUseCase

    @Before
    fun setup() {
        useCase = GetCitiesByQueryUseCase(mockCityRepository)
        coEvery { mockCityRepository.getAllCities() } returns testCities
    }


    @Test
    fun `invoke should return all cities when query is blank`() = runTest {
        // When
        val result = useCase("")

        // Then
        assertEquals(testCities, result)
        coVerify(exactly = 1) { mockCityRepository.getAllCities() }
    }

    @Test
    fun `invoke should return filtered cities when query matches`() = runTest {
        // When
        val result = useCase("lon") // Should match "London" and "Lyon"

        // Then
        assertEquals(1, result.size)
        assertTrue(result.any { it.name == "London" })
    }

    @Test
    fun `invoke should return empty list when no matches found`() = runTest {
        // When
        val result = useCase("Berlin")

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `invoke should be case insensitive`() = runTest {
        // When
        val lowerCase = useCase("london")
        val upperCase = useCase("LONDON")
        val mixedCase = useCase("LoNdOn")

        // Then
        assertEquals(lowerCase, upperCase)
        assertEquals(upperCase, mixedCase)
    }
}