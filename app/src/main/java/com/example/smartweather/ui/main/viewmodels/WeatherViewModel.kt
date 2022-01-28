package com.example.smartweather.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neloproyect.loader.LoadingState
import com.example.neloproyect.utils.singleArgViewModelFactory
import com.example.smartweather.ui.main.daos.WeatherDao
import com.example.smartweather.ui.main.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel (private val  repository :  WeatherRepository ) : ViewModel() {

    companion   object {
        val IDSERVICE = "1c86bf29518b4f4f7e07a384b5080496"
        /**
         * Factory for creating [TransactionViewModel]
         *
         * @param arg the repository to pass to [TransactionViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::WeatherViewModel)
    }


    private var mutablelistWeathers : MutableLiveData<List<WeatherDao>> = MutableLiveData()

     val LivedataListWeathers : LiveData<List<WeatherDao>> get() = mutablelistWeathers



    private val _loading = MutableLiveData<LoadingState>()

    val loading: LiveData<LoadingState> get() = _loading


    private val _txtMensaje = MutableLiveData<String>()

    val txtMensaje: LiveData<String> get() = _txtMensaje

/*+
    Implements the service lat long and dates.
 */

    fun listWeathersTransaction(lat:Long,Longi:Long,listdates: List<Long>){

        viewModelScope.launch(Dispatchers.IO){
            val listWeathers = ArrayList<WeatherDao>()
            try {

                _loading.postValue(LoadingState.LOADING)
                listdates.forEach {
                    var bzkMap: HashMap<String, String> = HashMap<String, String>()


                    bzkMap.put("dt",it.toString())
                    Log.i("After put: " , bzkMap.toString())
                    bzkMap.put("lat", lat.toString())
                    Log.i("After put: " , bzkMap.toString())
                    bzkMap.put("lon",Longi.toString())
                    Log.i("After put: " , bzkMap.toString())
                    bzkMap.put("appid",IDSERVICE)
                    Log.i("After put: " , bzkMap.toString())



                   val weather =  repository.getWeatherFromService(bzkMap)
                    listWeathers.add(weather)
                }
                _loading.postValue(LoadingState.LOADED)
                mutablelistWeathers.postValue(listWeathers)

            }catch (exe :Exception){
                Log.e("Error",exe.message!!)
                _txtMensaje.postValue(exe.message)
                _loading.postValue(LoadingState.error(exe.message))
            }


        }
    }




}