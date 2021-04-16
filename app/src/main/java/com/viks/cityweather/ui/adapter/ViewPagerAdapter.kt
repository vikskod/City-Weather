package com.viks.cityweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viks.cityweather.R
import com.viks.cityweather.databinding.ItemViewPagerBinding

class ViewPagerAdapter(
    val data: List<Int>
) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_pager, parent, false)
        val binding = ItemViewPagerBinding.bind(view)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentText = data[position]

        holder.binding.tvTemperature.text = currentText.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}