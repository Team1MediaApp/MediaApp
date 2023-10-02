package com.example.mediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeRcvItemTrendingBinding

class HomeTrendingRcvAdapter( private val itemClick: ItemClick) :
    ListAdapter<Item,HomeTrendingRcvViewHolder>(DifferCallback.differCallback)
    {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HomeTrendingRcvViewHolder {
            return HomeTrendingRcvViewHolder(
                HomeRcvItemTrendingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: HomeTrendingRcvViewHolder, position: Int) {
            val item = currentList[position]
            holder.itemView.setOnClickListener {
                itemClick?.onClick(item)
            }
            val category = currentList[position]
            val snippet = category.snippet
            val thumbnails = snippet.thumbnails

            holder.apply {
                holder.trendingThumbnails.load(thumbnails.medium.url)
                holder.trendingCount.text = "${position + 1}/10"
            }
        }
    }


class HomeTrendingRcvViewHolder(binding: HomeRcvItemTrendingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val trendingThumbnails: ImageView = binding.homeImgTrending
    val trendingCount: TextView = binding.homeTvTrendingCount


}