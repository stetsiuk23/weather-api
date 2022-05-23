package com.stetsiuk.weatherapi.di

import com.stetsiuk.weatherapi.repository.net.MainData.geocodingBaseUrl
import com.stetsiuk.weatherapi.repository.net.MainData.oneCallBaseUrl
import com.stetsiuk.weatherapi.api.GeocodingService
import com.stetsiuk.weatherapi.api.OneCallService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOneCallService(): OneCallService =
        Retrofit.Builder()
            .baseUrl(oneCallBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OneCallService::class.java)

    @Provides
    @Singleton
    fun provideGeocodingService(): GeocodingService =
        Retrofit.Builder()
            .baseUrl(geocodingBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingService::class.java)
}