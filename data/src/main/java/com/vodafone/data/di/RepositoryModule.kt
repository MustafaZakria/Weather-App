package com.vodafone.data.di

import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.CityRepositoryImpl
import com.vodafone.data.repository.WeatherRepository
import com.vodafone.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCityRepository(
        impl: CityRepositoryImpl
    ): CityRepository

    @Binds
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}