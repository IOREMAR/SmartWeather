package com.example.smartweather.ui.main.bindingAdapters


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neloproyect.fragmenttransaction.adapters.TransactionsAdapter
import com.example.neloproyect.loader.LoadingState
import com.example.smartweather.ui.main.daos.WeatherDao
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import com.example.smartweather.R


@BindingAdapter(value = ["SetUpTimeStamp"]  )
fun SetUpTimeStamp(view:TextView,time:Int){

    val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val timestamp = time.toLong() // timestamp in Long


    val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
        .format(java.time.Instant.ofEpochSecond(timestamp))

    Log.d("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z


    val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

    Log.d("parseTesting", date.dayOfWeek.toString()) // prints Wednesday
    Log.d("parseTesting", date.month.toString()) // prints August

    view.text =  date.dayOfWeek.toString()

}

@BindingAdapter(value = ["setupVisibility"])
fun ProgressBar.progressVisibility(loadingState: LoadingState?) {
    loadingState?.let {
        isVisible = when(it.status) {
            LoadingState.Status.RUNNING -> true
            LoadingState.Status.SUCCESS -> false
            LoadingState.Status.FAILED -> false
        }
    }

}

/**
 * @setupRecyclerView Is the Binding Adapter that recives the List Of Transactions. and set the Adapter for
 * the RecyclcerView
 */
@BindingAdapter(value = ["setupRecyclerView"])
fun setupRecyclerView(view: RecyclerView, listTransactions: List<WeatherDao>?) {
    val linearLayoutManager = LinearLayoutManager(view.context)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    linearLayoutManager.setAutoMeasureEnabled(true)
    listTransactions?.let {
        view.setNestedScrollingEnabled(false)
        view.setHasFixedSize(false)
        view.setLayoutManager(linearLayoutManager)
        view.adapter = TransactionsAdapter(listTransactions ,
            object :  TransactionsAdapter.WeatherSelectionListener {
            override fun onClick(Vista: View, weatherDao: WeatherDao) {
                val bundle = Bundle()
                bundle.putSerializable("Weather", weatherDao) // Serializable Object
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)

            }
        } )
    }
}



/**
 * @setupVisibilityTextView  Is the Binding Adapter That Uses the state of the result Transaction Passed by ViewModel
 * to handle the @Visibility for the Error Message
 */
@BindingAdapter(value = ["setupVisibilityTextView"])
fun setupVisibilityTextView(view: TextView , loadingState: LoadingState?) {
    loadingState?.let {
        view.isVisible  = when(it.status) {
            LoadingState.Status.RUNNING -> false
            LoadingState.Status.SUCCESS -> false
            LoadingState.Status.FAILED -> true
        }
    }

}

