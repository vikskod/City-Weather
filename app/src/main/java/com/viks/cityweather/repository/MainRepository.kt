package com.viks.cityweather.repository

import com.viks.cityweather.data.model.WeatherResponse
import com.viks.cityweather.util.Resource

interface MainRepository {

    suspend fun getCityWeather(city: String): Resource<WeatherResponse>

    suspend fun getLatLonWeather(lat: Double, lon: Double): Resource<WeatherResponse>
}