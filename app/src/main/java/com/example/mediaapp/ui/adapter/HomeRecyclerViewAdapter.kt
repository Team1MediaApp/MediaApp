package com.example.mediaapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeRcvItemViewBinding

class HomeRecyclerViewAdapter() :
    ListAdapter<Item,HomeRecyclerViewHolder>(differCallback) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return HomeRecyclerViewHolder(
            HomeRcvItemViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val category = currentList[position]
        val snippet = category.snippet
        val thumbnails = snippet.thumbnails
        Log.d("TAG", "onBindViewHolder: category ${category}, snippet : ${snippet}, thumbnails : ${thumbnails}")

        holder.apply {
            holder.testThumbnails.load(thumbnails.medium.url)
            holder.testItemID.text = snippet.title
        }
    }
        companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem.etag == newItem.etag
            }
        }
    }
}

class HomeRecyclerViewHolder(private val binding: HomeRcvItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val testThumbnails : ImageView = binding.rcvItemImgTest
    val testItemID:TextView = binding.rcvItemTvCategory
}




