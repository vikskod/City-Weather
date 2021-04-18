package com.viks.cityweather.data.model.current


import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import com.viks.cityweather.data.model.Weather
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    var isFromLocation: Boolean
) : Parcelable