package com.viks.cityweather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    @SuppressLint("SimpleDateFormat")
    fun getMyFormat(givenMillis: Long): String {
        val dateFormat = SimpleDateFormat("EEE, d MMM")
        return dateFormat.format(Date(givenMillis))
    }
}