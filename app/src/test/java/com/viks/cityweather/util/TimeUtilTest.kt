package com.viks.cityweather.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TimeUtilTest{

    @Test
    fun getMyFormat_Success() {
        val time = TimeUtil().getMyFormat(1619424571379)
        assertThat(time).isEqualTo("Mon, 26 Apr")
    }
}