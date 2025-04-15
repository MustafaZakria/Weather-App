package com.vodafone.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.vodafone.data.local.CityDao
import com.vodafone.data.local.CityDatabase
import com.vodafone.data.local.sharedpref.CitySharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CityDatabase {
        return Room.databaseBuilder(
            context,
            CityDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityDao(db: CityDatabase): CityDao = db.getDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app.prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCitySharedPreference(sharedPreferences: SharedPreferences): CitySharedPreference {
        return CitySharedPreference(sharedPreferences)
    }
}