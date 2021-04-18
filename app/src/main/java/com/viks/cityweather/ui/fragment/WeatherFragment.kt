package com.viks.cityweather.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.viks.cityweather.R
import com.viks.cityweather.data.model.current.WeatherResponse
import com.viks.cityweather.databinding.FragmentWeatherBinding
import com.viks.cityweather.util.Constant.ARG_OBJECT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

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
        }
    }

    fun newInstance(response: WeatherResponse): WeatherFragment {
        val f = WeatherFragment()
        val b = Bundle()
        b.putParcelable(ARG_OBJECT, response)
        f.arguments = b
        return f
    }
}