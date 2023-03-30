package com.romka_po.driveassistant

import com.romka_po.driveassistant.interfaces.ApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var productApi: ApiClient

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        productApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiClient::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCars() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getBrands()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body()!!.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetCars_returnCars() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/resource_test.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = productApi.getBrands()
        mockWebServer.takeRequest()
        Assert.assertEquals(false, response.body()!!.isEmpty())
        Assert.assertEquals(2, response.body()!!.size)
    }


    @After
    fun remove(){
        mockWebServer.shutdown()
    }
}