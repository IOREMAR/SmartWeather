package com.example.smartweather.ui.main.repository

import android.util.Log
import android.util.MalformedJsonException
import com.example.smartweather.ui.main.daos.WeatherDao
import com.example.smartweather.ui.main.repository.network.WeatherNetwork
import kotlinx.coroutines.CompletableDeferred
import java.lang.RuntimeException

class WeatherRepository {

    private val NetworkService = WeatherNetwork

 suspend fun getWeatherFromService(params : Map<String,String> ) :WeatherDao {

     val weatherResponseObject = CompletableDeferred<WeatherDao>()

     try {
         val serviceResponseObject = NetworkService.getWeatherService(params)
         if(serviceResponseObject.isSuccessful){
             weatherResponseObject.complete(serviceResponseObject.body()!!)
         }else{
             Log.e("Error-Res",serviceResponseObject.message())
            throw RuntimeException(serviceResponseObject.errorBody().toString())
         }
     }

     catch (exe : MalformedJsonException){
         Log.e("Error",exe.message!!)
         throw Exception("Favor de Intentarlo Mas Tarde")
     }
     catch (exe: RuntimeException){
         Log.e("Error",exe.message!!)
         throw exe
     }

     return weatherResponseObject.await()

 }


}