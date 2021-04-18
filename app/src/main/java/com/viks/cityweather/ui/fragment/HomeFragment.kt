package com.viks.cityweather.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fondesa.kpermissions.*
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import com.viks.cityweather.R
import com.viks.cityweather.databinding.FragmentHomeBinding
import com.viks.cityweather.ui.adapter.FragmentViewPagerAdapter
import com.viks.cityweather.ui.viewmodel.HomeFragmentViewModel
import com.viks.cityweather.util.Constant
import com.viks.cityweather.util.Status
import com.viks.cityweather.util.showPermanentlyDeniedDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), PermissionRequest.Listener {

    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
        ).build()
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private val cityList = Constant.cityList
    private var dataList: MutableList<WeatherFragment> = ArrayList()

    private lateinit var pagerAdapter: FragmentViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        pagerAdapter = FragmentViewPagerAdapter(this, dataList)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()

        initObservers()

        request.addListener(this)
        request.addListener {
            if (it.anyGranted()) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        //TODO check network is available or not
                        viewModel.getAllLocationWeather(location, cityList)
                    }
            }

            if (it.anyDenied()) {
                Toast.makeText(
                    requireContext(),
                    R.string.additional_listener_msg,
                    Toast.LENGTH_SHORT
                ).show()
                //TODO check network is available or not
                viewModel.getAllLocationWeather(null, cityList)
            }
        }
        request.send()
    }

    private fun initObservers() {
        // Observe data from LiveData
        viewModel.allWeatherResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    dataList.clear()
                    it.data?.let { d ->
                        for (response in d) {
                            dataList.add(WeatherFragment().newInstance(response))
                        }
                    }
                    pagerAdapter.notifyDataSetChanged()
                    hideProgressBar()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                Status.ERROR -> {
                    hideProgressBar()
                    //TODO Handle error
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

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        val context = requireContext()
        when {
            result.anyPermanentlyDenied() -> {
                context.showPermanentlyDeniedDialog(result)
            }
            result.anyShouldShowRationale() -> {
                //context.showRationaleDialog(result, request)
            }
            result.allGranted() -> {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            }
        }
    }
}