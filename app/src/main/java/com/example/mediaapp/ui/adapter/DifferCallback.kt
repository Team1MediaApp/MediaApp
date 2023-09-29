package com.example.mediaapp.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
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
}

interface ItemClick {
    fun onClick(view: View, position: Int)
}