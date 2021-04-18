package com.viks.cityweather.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.viks.cityweather.ui.fragment.WeatherFragment

class FragmentViewPagerAdapter(f: Fragment, private val allFragments: List<WeatherFragment>) :
    FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return allFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return allFragments[position]
    }
}