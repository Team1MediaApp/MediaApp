package com.example.mediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeRcvItemCategoryBinding

class HomeCategoryRcvViewAdapter(private val itemClick: ItemClick) :
    ListAdapter<Item,HomeCategoryRcvHolder>(DifferCallback.differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryRcvHolder {
        return HomeCategoryRcvHolder(
            HomeRcvItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeCategoryRcvHolder, position: Int) {
        val item = currentList[position]
        holder.itemView.setOnClickListener {
            itemClick?.onClick(item)
        }
        val category = currentList[position]
        val snippet = category.snippet
        val thumbnails = snippet.thumbnails


        holder.apply {
            holder.testThumbnails.load(thumbnails.medium.url)
            holder.testItemID.text = snippet.title

        }
    }
}

class HomeCategoryRcvHolder(binding: HomeRcvItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val testThumbnails : ImageView = binding.homeRcvImgCategoryVideoThumbnail
    val testItemID:TextView = binding.homeRcvTvCategoryVideoTitle
}