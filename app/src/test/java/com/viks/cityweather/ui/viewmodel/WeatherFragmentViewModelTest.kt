package com.viks.cityweather.ui.viewmodel

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.viks.cityweather.MainCoroutineRule
import com.viks.cityweather.getOrAwaitValueTest
import com.viks.cityweather.repository.FakeMainRepository
import com.viks.cityweather.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherFragmentViewModelTest {

    // Make sure everything runs on same thread, means one action after another
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set coroutine rule
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WeatherFragmentViewModel

    private lateinit var location: Location

    @Before
    fun setUp() {
        viewModel = WeatherFragmentViewModel(FakeMainRepository())
    }

    @Test
    fun `getAllLocationWeather has status SUCCESS`() {
        viewModel.getForecastWeather(-33.8679, 151.2073)
        val value = viewModel.forecastWeatherResponse.getOrAwaitValueTest()

        Truth.assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}