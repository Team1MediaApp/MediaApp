package com.example.mediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.databinding.SearchHistoryItemBinding

class SearchHistoryLIstAdapter : RecyclerView.Adapter<SearchHistoryLIstAdapter.ViewHolder>() {
    private val listData: ArrayList<String> = arrayListOf()

    fun updateData(items: ArrayList<String>) {
        listData.clear()
        listData.addAll(items)
    }

    class ViewHolder(private val binding: SearchHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            itemHistoryQueryTxt.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }
}