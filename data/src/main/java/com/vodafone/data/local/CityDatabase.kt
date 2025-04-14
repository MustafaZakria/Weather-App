package com.vodafone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vodafone.data.local.model.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class CityDatabase: RoomDatabase() {
    abstract fun getDao(): CityDao
}