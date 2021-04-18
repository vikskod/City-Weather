package com.viks.cityweather.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.viks.cityweather.R
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.data.model.forecast.Daily
import com.viks.cityweather.databinding.FragmentWeatherBinding
import com.viks.cityweather.ui.adapter.ForecastAdapter
import com.viks.cityweather.ui.viewmodel.WeatherFragmentViewModel
import com.viks.cityweather.util.Constant.ARG_OBJECT
import com.viks.cityweather.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Viewpager2 holds WeatherFragment
 * WeatherFragment shows Current weather and 3 days weather forecast
 * Contains general views and RecyclerView
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherFragmentViewModel by viewModels()

    @Inject
    lateinit var mAdapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherBinding.bind(view)

        val response: WeatherResponse? = arguments?.getParcelable(ARG_OBJECT)
        if (response != null) {
            binding.tvLocation.text = response.name
            binding.tvTemperature.text = response.main.temp.toInt().toString()

            val spannable =
                SpannableString("${response.main.tempMax.toInt()}\u00B0 / ${response.main.tempMin.toInt()}\u00B0")
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.tvMaxMin.text = spannable
            binding.tvWeatherCondition.text = response.weather[0].main

            if (!response.isFromLocation)
                binding.ivLocation.visibility = View.GONE

            when (response.weather[0].main) {
                "Clouds" -> {
                    binding.ivBackground.setImageDrawable(requireContext().getDrawable(R.drawable.bg_light_cloud))
                }
                "Clear" -> {
                    binding.ivBackground.setImageDrawable(requireContext().getDrawable(R.drawable.bg_clean_sky))
                }
                "Rain" -> {
                    binding.ivBackground.setImageDrawable(requireContext().getDrawable(R.drawable.bg_water_drop))
                }
                else -> {
                    binding.ivBackground.setImageDrawable(requireContext().getDrawable(R.drawable.bg_light_cloud))
                }
            }

            initRecyclerView()
            initObservers()
            //TODO check network is available or not
            viewModel.getForecastWeather(response.coord.lat, response.coord.lon)
        }
    }

    fun newInstance(response: WeatherResponse): WeatherFragment {
        val f = WeatherFragment()
        val b = Bundle()
        b.putParcelable(ARG_OBJECT, response)
        f.arguments = b
        return f
    }

    private fun initRecyclerView() {
        binding.rvFutureWeather.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /*
    * Observe data from LiveData
    * Remove today's weather from Forecast using drop method
    * Send data to Recyclerview Adapter
    * */
    private fun initObservers() {
        viewModel.forecastWeatherResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    mAdapter.differ.submitList(it.data?.daily?.drop(1))
                    binding.rvFutureWeather.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.rvFutureWeather.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.rvFutureWeather.visibility = View.GONE
                }
            }
        })
    }
}