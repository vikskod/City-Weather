package com.viks.cityweather.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.viks.cityweather.R
import com.viks.cityweather.data.model.WeatherResponse
import com.viks.cityweather.databinding.FragmentHomeBinding
import com.viks.cityweather.ui.adapter.ViewPagerAdapter
import com.viks.cityweather.ui.viewmodel.HomeFragmentViewModel
import com.viks.cityweather.util.Resource
import com.viks.cityweather.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private val cityList = listOf("Sydney", "Perth", "Hobart")
    private var datList: MutableList<WeatherResponse> = ArrayList()

    private val pagerAdapter = ViewPagerAdapter(datList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()

        initObservers()

        viewModel.getAllLocationWeather(0, 0, cityList)
    }

    private fun initObservers() {
        // Observe data from LiveData
        viewModel.allWeatherResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { d -> datList.addAll(d) }
                    pagerAdapter.notifyDataSetChanged()
                    hideProgressBar()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                Status.ERROR -> {
                    hideProgressBar()
                    //TODO Handle error situation
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}