package com.example.neloproyect.fragmenttransaction.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartweather.databinding.TransactionsItemBinding
import com.example.smartweather.ui.main.daos.WeatherDao
import java.util.*

/**
 * @TransactionsAdapter Is the Binding adapter for the Transaction Data.
 */
class TransactionsAdapter (private val data : List<WeatherDao>) : RecyclerView.Adapter<TransactionsAdapter.ViewHolderAdapter>() {

    inner class ViewHolderAdapter(val binding: TransactionsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * @bind Uses Item : @TransactionsDao to asing the Data to the View By DataBinding ->  @dataItem
         */
        fun bind(item: WeatherDao?) {
            binding.dataItem = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TransactionsItemBinding.inflate(inflater,parent,false)
        return ViewHolderAdapter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) = holder.bind(data.get(position))

    override fun getItemCount(): Int = data.size



}