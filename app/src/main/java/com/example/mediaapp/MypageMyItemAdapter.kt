package com.example.mediaapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediaapp.data.MyDataModel
import com.example.mediaapp.databinding.MypageItemBinding


class MypageMyItemAdapter(var mContext: Context, private val mItems: MutableList<MyDataModel>) :
    RecyclerView.Adapter<MypageMyItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MypageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(mContext)
            .load(mItems[position].myThumbnailUrl)
            .into(holder.itemImageView)
        holder.itemTitle.setText(mItems[position].myTitle)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(binding: MypageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemImageView = binding.mypageItemMadiaImg
        val itemTitle = binding.mypageItemMediaTitle
    }
}