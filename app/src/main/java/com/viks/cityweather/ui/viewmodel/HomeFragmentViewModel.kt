package com.viks.cityweather.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.repository.MainRepository
import com.viks.cityweather.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for HomeFragment
 * Using LiveData to pass the REST API data to the HomeFragment
 */

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
    private val _allWeatherResponse: MutableLiveData<Resource<List<WeatherResponse>>> =
        MutableLiveData()
    val allWeatherResponse: LiveData<Resource<List<WeatherResponse>>> get() = _allWeatherResponse

    fun getAllLocationWeather(
        location: Location?,
        cities: List<String>
    ) {

        viewModelScope.launch {
            _allWeatherResponse.postValue(Resource.loading(null))

            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                val allWeather = mutableListOf<WeatherResponse>()

                // If location is known show current weather of last know location
                if (location != null) {
                    val weatherFromLatLonDeferred =
                        async { repository.getLatLonWeather(location.latitude, location.longitude) }
                    val weatherFromLatLon = weatherFromLatLonDeferred.await()
                    weatherFromLatLon.data?.let {
                        it.isFromLocation = true
                        allWeather.add(it)
                    }
                }

                for (city in cities) {
                    val weatherFromCityDeferred = async { repository.getCityWeather(city) }
                    val weatherFromCity = weatherFromCityDeferred.await()
                    weatherFromCity.data?.let { allWeather.add(it) }
                }

                if (allWeather.size > 0)
                    _allWeatherResponse.postValue(Resource.success(allWeather))
                else  _allWeatherResponse.postValue(Resource.error("Something Went Wrong", null))
            } catch (e: Exception) {
                _allWeatherResponse.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }
}