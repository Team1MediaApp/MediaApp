package com.example.mediaapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.mediaapp.data.model.channel.ChItem
import com.example.mediaapp.data.model.video.Item

object DifferCallback{
    val differCallback = object : DiffUtil.ItemCallback<Item>() {
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

    val channelDifferCallback = object : DiffUtil.ItemCallback<ChItem>() {
        override fun areContentsTheSame(
            oldChItem: ChItem,
            newChItem: ChItem
        ): Boolean {
            return oldChItem == newChItem
        }

        override fun areItemsTheSame(
            oldChItem: ChItem,
            newChItem: ChItem
        ): Boolean {
            return oldChItem.etag == newChItem.etag
        }
    }
}

interface ItemClick {
    fun onClick(view: View, position: Int)
}