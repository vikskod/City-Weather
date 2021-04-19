package com.viks.cityweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("max")
    val max: Double,
    @SerializedName("min")
    val min: Double
)