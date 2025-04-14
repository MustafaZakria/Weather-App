package com.vodafone.data.di

import android.content.Context
import com.vodafone.data.local.util.CityJsonLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataLoaderModule {

    @Provides
    fun provideCityJsonLoader(
        @ApplicationContext context: Context,
    ): CityJsonLoader {
        return CityJsonLoader(context)
    }

}