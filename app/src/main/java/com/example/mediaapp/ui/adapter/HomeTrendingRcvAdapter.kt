package com.example.mediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeRcvItemTrendingBinding

class HomeTrendingRcvAdapter() :
    ListAdapter<Item,HomeTrendingRcvViewHolder>(DifferCallback.differCallback){

    private val itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTrendingRcvViewHolder {
        return HomeTrendingRcvViewHolder(
            HomeRcvItemTrendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: HomeTrendingRcvViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val category = currentList[position]
        val snippet = category.snippet
        val thumbnails = snippet.thumbnails

        holder.apply {
            holder.trendingThumbnails.load(thumbnails.medium.url)
            holder.trendingCount.text = "$position/10"
        }
    }


}


class HomeTrendingRcvViewHolder(binding: HomeRcvItemTrendingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val trendingThumbnails: ImageView = binding.homeImgTrending
    val trendingCount : TextView = binding.homeTvTrendingCount


}