package com.example.mediaapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.MypageItemBinding

interface OnItemClickListener{ //interface가 뭔지 좀 더 알아보기
    fun onItemClick(item: Item)
}
class MypageMyItemAdapter(var mContext: Context, private val mItems: MutableList<Item>, private val itemClickListener : OnItemClickListener) :
    RecyclerView.Adapter<MypageMyItemAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MypageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemBox.setOnClickListener{
            itemClickListener.onItemClick(mItems[position])  //mContext는 저장하는 공간 , mItems는 저장하는 데이터 그 자체임,
        }
        Glide.with(mContext)
            .load(mItems[position].snippet.thumbnails.medium.url)
            .into(holder.itemImageView)
        holder.itemTitle.setText(mItems[position].snippet.title)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(binding: MypageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemBox = binding.mypageConstBox
        val itemImageView = binding.mypageItemMadiaImg
        val itemTitle = binding.mypageItemMediaTitle
    }
}