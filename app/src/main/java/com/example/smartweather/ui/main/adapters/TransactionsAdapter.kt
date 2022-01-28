package com.example.neloproyect.fragmenttransaction.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartweather.databinding.TransactionsItemBinding
import com.example.smartweather.ui.main.daos.WeatherDao
import java.util.*

/**
 * @TransactionsAdapter Is the Binding adapter for the Transaction Data.
 */
class TransactionsAdapter (private val data : List<WeatherDao>   ,  private val Listener : WeatherSelectionListener
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolderAdapter>() {

    inner class ViewHolderAdapter(val binding: TransactionsItemBinding , val cliclListener :WeatherSelectionListener ) : RecyclerView.ViewHolder(binding.root) {

        private var WeatherLister : WeatherSelectionListener? =null
        private var Weather : WeatherDao?= null
        /**
         * @bind Uses Item : @TransactionsDao to asing the Data to the View By DataBinding ->  @dataItem
         */
        fun bind(item: WeatherDao?) {
            binding.dataItem = item
            Weather = item
            WeatherLister = cliclListener
           binding.root.setOnClickListener {
               WeatherLister?.onClick(it,Weather!!)
           }

            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionsItemBinding.inflate(inflater,parent,false)
        return ViewHolderAdapter(binding,Listener)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) = holder.bind(data.get(position) )

    override fun getItemCount(): Int = data.size

     interface WeatherSelectionListener {
    fun onClick(Vista : View, weatherDao: WeatherDao)
    }

}