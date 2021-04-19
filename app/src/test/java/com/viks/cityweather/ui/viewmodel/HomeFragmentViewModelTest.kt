package com.viks.cityweather.ui.viewmodel

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.viks.cityweather.MainCoroutineRule
import com.viks.cityweather.getOrAwaitValueTest
import com.viks.cityweather.repository.FakeMainRepository
import com.viks.cityweather.util.Constant.cityList
import com.viks.cityweather.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeFragmentViewModelTest {

    // Make sure everything runs on same thread, means one action after another
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set coroutine rule
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeFragmentViewModel

    private lateinit var location: Location

    @Before
    fun setUp() {
        viewModel = HomeFragmentViewModel(FakeMainRepository())
    }

    @Test
    fun `getAllLocationWeather has status SUCCESS`() {
        location = Location("")
        location.latitude = -33.8679
        location.longitude = 151.2073

        viewModel.getAllLocationWeather(location, cityList)
        val value = viewModel.allWeatherResponse.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}