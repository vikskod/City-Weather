package com.viks.cityweather.data.network

import com.viks.cityweather.BuildConfig
import com.viks.cityweather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Get  weather by city name
    @GET("/weather")
    suspend fun getWeatherByCity(
        @Query("city") city: String,
        @Query("appid") appId: String = BuildConfig.BASE_URL
    ): Response<WeatherResponse>

    // Get  weather by lat/lon
    @GET("/weather")
    suspend fun getWeatherByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = BuildConfig.BASE_URL
    ): Response<WeatherResponse>
}