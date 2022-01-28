package com.example.smartweather.ui.main.repository.network

import com.example.smartweather.ui.main.daos.WeatherDao
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

object WeatherNetwork {


    private val URL_Serivce = "http://api.openweathermap.org/"


    lateinit var WeatherService: TransactionsApi

    init {
        createTransactionService()
    }

    /**
     * Creates the Retrofit Client Prepare the TransactionService to Use.
     */
    private fun createTransactionService() {

        // Creamos un interceptor y le indicamos el log level a usar
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Asociamos el interceptor a las peticiones
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(URL_Serivce)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        WeatherService = retrofit.create(TransactionsApi::class.java)
    }

    suspend fun getWeatherService(params: Map<String,String>)  : Response<WeatherDao>{
        return WeatherService.getTransactions(params)
    }

    interface  TransactionsApi {
        @GET("data/2.5/onecall/timemachine")
        suspend fun getTransactions(@QueryMap params: Map<String,String> ): Response<WeatherDao>
    }


}