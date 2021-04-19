package com.viks.cityweather.repository

import com.viks.cityweather.data.model.Weather
import com.viks.cityweather.data.model.current.Coord
import com.viks.cityweather.data.model.current.Main
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.data.model.forecast.Daily
import com.viks.cityweather.data.model.forecast.ForecastResponse
import com.viks.cityweather.data.model.forecast.Temp
import com.viks.cityweather.util.Resource

class FakeMainRepository : MainRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(b: Boolean) {
        shouldReturnNetworkError = b
    }

    override suspend fun getCityWeather(city: String): Resource<WeatherResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            val coord = Coord(
                149.1281,
                -35.2835
            )
            val main = Main(
                286.06,
                285.03,
                285.15,
            )
            val weather = listOf(
                Weather(
                    "clear sky",
                    "01d",
                    800,
                    "Clear",
                )
            )
            Resource.success(
                WeatherResponse(
                    "stations",
                    200,
                    coord,
                    1618792835,
                    2172517,
                    main,
                    "Canberra",
                    36000,
                    10000,
                    weather,
                    true
                )
            )
        }
    }

    override suspend fun getLatLonWeather(lat: Double, lon: Double): Resource<WeatherResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            val coord = Coord(
                149.1281,
                -35.2835
            )
            val main = Main(
                286.06,
                285.03,
                285.15,
            )
            val weather = listOf(
                Weather(
                    "clear sky",
                    "01d",
                    800,
                    "Clear",
                )
            )
            Resource.success(
                WeatherResponse(
                    "stations",
                    200,
                    coord,
                    1618792835,
                    2172517,
                    main,
                    "Canberra",
                    36000,
                    10000,
                    weather,
                    true
                )
            )
        }
    }

    override suspend fun getForecastWeather(lat: Double, lon: Double): Resource<ForecastResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {

            val temp = Temp(
                285.63,
                295.38
            )

            val weather = listOf(
                Weather(
                    "clear sky",
                    "01d",
                    800,
                    "Clear",
                )
            )

            val daily = listOf<Daily>(
                Daily(
                    1618880400,
                    temp,
                    weather
                )
            )

            Resource.success(
                ForecastResponse(
                    daily,
                    -33.8679,
                    151.2073,
                )
            )
        }
    }
}