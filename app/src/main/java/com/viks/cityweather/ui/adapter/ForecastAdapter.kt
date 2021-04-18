package com.viks.cityweather.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.viks.cityweather.R
import com.viks.cityweather.data.model.forecast.Daily
import com.viks.cityweather.databinding.RvItemForecastBinding
import com.viks.cityweather.util.TimeUtil

class ForecastAdapter(val context: Context) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(private val binding: RvItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(data: Daily) {
            binding.tvTime.text = TimeUtil().getMyFormat(data.dt.toLong() * 1000)
            val spannable =
                SpannableString("${data.temp.max.toInt()}\u00B0 / ${data.temp.min.toInt()}\u00B0")
            spannable.setSpan(StyleSpan(Typeface.BOLD), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.tvMaxMin.text = spannable

            when (data.weather[0].main) {
                "Clouds" -> {
                    binding.ivWeather.setImageDrawable(context.getDrawable(R.drawable.ic_cloud))
                }
                "Clear" -> {
                    binding.ivWeather.setImageDrawable(context.getDrawable(R.drawable.ic_clear))
                }
                "Rain" -> {
                    binding.ivWeather.setImageDrawable(context.getDrawable(R.drawable.ic_rain))
                }
                else -> {
                    binding.ivWeather.setImageDrawable(context.getDrawable(R.drawable.ic_cloud))
                }
            }
        }
    }

    // Helper for computing the difference between two lists
    private val callback = object : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            RvItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size < 3)
            differ.currentList.size
        else 3
    }
}