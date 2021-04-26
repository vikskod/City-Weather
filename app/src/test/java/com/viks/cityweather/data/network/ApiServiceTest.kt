package com.viks.cityweather.data.network

import com.google.common.truth.Truth.assertThat
import com.viks.cityweather.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getWeatherByCity_sendRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("cityresponse.json")
            val responseBody = service.getWeatherByCity("sydney").body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/weather?q=sydney&appid=${BuildConfig.API_KEY}&units=metric")
        }
    }

    @Test
    fun getWeatherByCity_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("cityresponse.json")
            val responseBody = service.getWeatherByCity("sydney").body()
            val coord = responseBody!!.coord
            val lat = coord.lat
            val lon = coord.lon

            assertThat(lon).isEqualTo(151.2073)
            assertThat(lat).isEqualTo(-33.8679)
        }
    }
}