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
    val allWeatherResponse: LiveData<Resource<ForecastResponse>> get() = _forecastWeatherResponse

    fun getForecastWeather(
        location: Location?,
        cities: List<String>
    ) {

        viewModelScope.launch {

        }
    }
}