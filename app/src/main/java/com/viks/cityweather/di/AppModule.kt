package com.viks.cityweather.di

import com.viks.cityweather.BuildConfig
import com.viks.cityweather.data.network.ApiService
import com.viks.cityweather.repository.DefaultMainRepository
import com.viks.cityweather.repository.MainRepository
import com.viks.cityweather.ui.adapter.ForecastAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.ocpsoft.prettytime.PrettyTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiService): MainRepository = DefaultMainRepository(api)

    @Provides
    fun provideForecastAdapter(): ForecastAdapter = ForecastAdapter()

    @Singleton
    @Provides
    fun providePrettyTime(): PrettyTime = PrettyTime(Locale.getDefault())
}