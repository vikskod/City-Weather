package com.viks.cityweather.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viks.cityweather.data.model.forecast.ForecastResponse
import com.viks.cityweather.repository.DefaultMainRepository
import com.viks.cityweather.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(private val repository: DefaultMainRepository) :
    ViewModel() {
    private val _forecastWeatherResponse: MutableLiveData<Resource<ForecastResponse>> =
        MutableLiveData()
    val forecastWeatherResponse: LiveData<Resource<ForecastResponse>> get() = _forecastWeatherResponse

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
