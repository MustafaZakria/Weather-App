package com.vodafone.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vodafone.data.local.model.CityEntity

@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :cityId")
    suspend fun getCityById(cityId: Int): CityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)
}