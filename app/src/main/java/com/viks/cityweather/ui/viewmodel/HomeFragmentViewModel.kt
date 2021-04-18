package com.viks.cityweather.ui.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.repository.DefaultMainRepository
import com.viks.cityweather.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val repository: DefaultMainRepository) :
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
                coroutineScope {
                    val allWeather = mutableListOf<WeatherResponse>()

                    if (location != null) {
                        val weatherFromLatLonDeferred =
                            async { repository.getLatLonWeather(location.latitude, location.longitude) }
                        val weatherFromLatLon = weatherFromLatLonDeferred.await()
                        weatherFromLatLon.data?.let {
                            it.isFromLocation = true
                            allWeather.add(it)
                        }
                    }

                    val weatherFromCity1Deferred = async { repository.getCityWeather(cities[0]) }
                    val weatherFromCity2Deferred = async { repository.getCityWeather(cities[1]) }
                    val weatherFromCity3Deferred = async { repository.getCityWeather(cities[2]) }


                    val weatherFromCity1 = weatherFromCity1Deferred.await()
                    val weatherFromCity2 = weatherFromCity2Deferred.await()
                    val weatherFromCity3 = weatherFromCity3Deferred.await()



                    weatherFromCity1.data?.let { allWeather.add(it) }
                    weatherFromCity2.data?.let { allWeather.add(it) }
                    weatherFromCity3.data?.let { allWeather.add(it) }

                    _allWeatherResponse.postValue(Resource.success(allWeather))

                }
            } catch (e: Exception) {
                _allWeatherResponse.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }
}