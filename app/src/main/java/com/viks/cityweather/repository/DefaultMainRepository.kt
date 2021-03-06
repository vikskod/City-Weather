package com.viks.cityweather.repository

import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.data.model.forecast.ForecastResponse
import com.viks.cityweather.data.network.ApiService
import com.viks.cityweather.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: ApiService
) : MainRepository {
    // Pull current weather using Location name
    override suspend fun getCityWeather(city: String): Resource<WeatherResponse> {
        return try {
            val response = api.getWeatherByCity(city)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message(), null)
        }catch (e: Exception){
            // Can be caused by json syntax exception
            Resource.error("Something Went Wrong", null)
        }
    }

    // Pull current weather using Location coordinates
    override suspend fun getLatLonWeather(lat: Double, lon: Double): Resource<WeatherResponse> {
        return try {
            val response = api.getWeatherByLatLon(lat, lon)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message(), null)
        }catch (e: Exception){
            Resource.error("Something Went Wrong", null)
        }
    }

    // Pull 8 days forecast from the api.
    override suspend fun getForecastWeather(lat: Double, lon: Double): Resource<ForecastResponse> {
        return try {
            val response = api.getForecast(lat, lon)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.success(result)
            else
                Resource.error(response.message(), null)
        }catch (e: Exception){
            Resource.error("Something Went Wrong", null)
        }
    }
}