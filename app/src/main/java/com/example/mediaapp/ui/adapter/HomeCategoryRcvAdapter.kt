package com.example.mediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.video.CategoryId
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeRcvItemCategoryBinding

class HomeCategoryRcvViewAdapter() :
    ListAdapter<Item,HomeCategoryRcvHolder>(DifferCallback.differCallback) {

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryRcvHolder {
        return HomeCategoryRcvHolder(
            HomeRcvItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeCategoryRcvHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val category = currentList[position]
        val snippet = category.snippet
        val thumbnails = snippet.thumbnails

//        category.snippet.categoryId

        holder.apply {
            holder.testThumbnails.load(thumbnails.medium.url)
            holder.testItemID.text = snippet.categoryId

            for (categoryName in snippet.categoryId){
                holder.testItemID.text = CategoryId.categoryMap[snippet.categoryId]
            }

        }
    }
}

class HomeCategoryRcvHolder(binding: HomeRcvItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val testThumbnails : ImageView = binding.rcvItemImgTest
    val testItemID:TextView = binding.rcvItemTvCategory
}





