package com.viks.cityweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
)