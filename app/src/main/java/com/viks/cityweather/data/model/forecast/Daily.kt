package com.viks.cityweather.data.model.forecast


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temp: Temp
)