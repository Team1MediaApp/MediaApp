package com.example.mediaapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mediaapp.databinding.SearchChannelItemBinding
import com.example.mediaapp.databinding.SearchVideoItemBinding

class SearchListAdapter : RecyclerView.Adapter<ViewHolder>() {
    private val dataList: ArrayList<Any> = arrayListOf()

    class VideoViewHolder(private val binding: SearchVideoItemBinding) : ViewHolder(binding.root) {

        fun bind() = with(binding) {}
    }

    class ChannelViewHolder(private val binding: SearchChannelItemBinding) :
        ViewHolder(binding.root) {

        fun bind() = with(binding) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return VideoViewHolder(
            SearchVideoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind()
    }
}