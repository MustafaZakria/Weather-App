package com.vodafone.data.di

import com.vodafone.data.repository.CityRepository
import com.vodafone.data.repository.CityRepositoryImpl
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
}