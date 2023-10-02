package com.example.mediaapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mediaapp.databinding.SearchChannelItemBinding

class SearchChannelListAdapter : RecyclerView.Adapter<ViewHolder>() {

    class ChannelViewHolder(private val binding: SearchChannelItemBinding) :
        ViewHolder(binding.root) {
        fun bind() = with(binding) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}