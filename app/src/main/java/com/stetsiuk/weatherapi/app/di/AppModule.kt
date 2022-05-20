package com.stetsiuk.weatherapi.app.di

import com.stetsiuk.weatherapi.repository.net.geocoding.services.GeocodingService
import com.stetsiuk.weatherapi.repository.net.onecall.services.OneCallService
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
    fun provideOneCallService(): OneCallService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OneCallService::class.java)

    @Provides
    @Singleton
    fun provideGeocodingService(): GeocodingService =
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingService::class.java)
}