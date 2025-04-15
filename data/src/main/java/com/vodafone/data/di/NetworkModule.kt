package com.vodafone.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vodafone.data.BuildConfig
import com.vodafone.data.remote.BASE_URL
import com.vodafone.data.remote.QUERY_API_KEY
import com.vodafone.data.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url.newBuilder()
                        .addQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
                        .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()
    }

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        networkJson: Json
    ): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                networkJson.asConverterFactory("application/json".toMediaType())
            ).build()
            .create(WeatherApi::class.java)
    }
}