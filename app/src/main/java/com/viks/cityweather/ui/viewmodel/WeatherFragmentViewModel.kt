package com.viks.cityweather.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viks.cityweather.data.model.forecast.ForecastResponse
import com.viks.cityweather.repository.MainRepository
import com.viks.cityweather.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for WeatherFragment
 * Using LiveData to pass the REST API data to the WeatherFragment
 */

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
    private val _forecastWeatherResponse: MutableLiveData<Resource<ForecastResponse>> =
        MutableLiveData()
    val forecastWeatherResponse: LiveData<Resource<ForecastResponse>> get() = _forecastWeatherResponse

    // Get weather forecast from the REST API
    fun getForecastWeather(
        lat: Double,
        lon: Double
    ) {

        viewModelScope.launch {
            _forecastWeatherResponse.postValue(Resource.loading(null))
            try {
                _forecastWeatherResponse.postValue(repository.getForecastWeather(lat, lon))
            } catch (e: Exception) {
                _forecastWeatherResponse.postValue(Resource.error(e.toString(), null))
            }
        }
    }
}
