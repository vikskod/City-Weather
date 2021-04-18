package com.viks.cityweather.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viks.cityweather.R
import com.viks.cityweather.data.model.WeatherResponse
import com.viks.cityweather.databinding.ItemViewPagerBinding

class ViewPagerAdapter(
    val data: List<WeatherResponse>
) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_pager, parent, false)
        val binding = ItemViewPagerBinding.bind(view)
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentText = data[position]

        holder.binding.tvLocation.text = currentText.name
        holder.binding.tvTemperature.text = currentText.main.temp.toInt().toString()

        val spannable =
            SpannableString("${currentText.main.tempMax.toInt()}\u00B0 / ${currentText.main.tempMin.toInt()}\u00B0")
        spannable.setSpan(StyleSpan(BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        holder.binding.tvMaxMin.text = spannable
        holder.binding.tvWeatherCondition.text = currentText.weather[0].main
    }

    override fun getItemCount(): Int {
        return data.size
    }
}