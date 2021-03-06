package com.viks.cityweather.data.network

import com.viks.cityweather.BuildConfig
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.data.model.forecast.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Get  weather by city name
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appId: String = BuildConfig.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>

    // Get  weather by lat/lon
    @GET("weather")
    suspend fun getWeatherByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>

    /*
    * Using getForecast we can get current weather
    * BUT in this api can't put City name to get weather data
    * */

    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.API_KEY,
        @Query("exclude") exclude: String = "alerts,hourly,minutely",
        @Query("units") units: String = "metric"
    ): Response<ForecastResponse>
}