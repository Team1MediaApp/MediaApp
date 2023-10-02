package com.example.mediaapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mediaapp.databinding.SearchVideoItemBinding

class SearchVideoListAdapter : RecyclerView.Adapter<ViewHolder>() {

    class VideoViewHolder(private val binding: SearchVideoItemBinding) : ViewHolder(binding.root) {
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