package com.example.mediaapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.mediaapp.databinding.SearchVideoItemBinding
import com.example.mediaapp.model.SearchVideoEntity
import com.example.mediaapp.model.YoutubeSearchResponse

class SearchVideoListAdapter() : RecyclerView.Adapter<SearchVideoListAdapter.ViewHolder>() {
    private val dataList: ArrayList<SearchVideoEntity> = arrayListOf()

    init {
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
        dataList.add(
            SearchVideoEntity(
                "ITSub잇섭",
                "형보다 괜찮은 아우? 애플 아이폰 15 달라진 핵심기능 7가지!",
                "https://i.ytimg.com/vi/GtWhC-ITUsI/mqdefault.jpg",
                "2023-09-28T05:00:08Z"
            )
        )
    }

    fun refreshList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun addDataList(items: ArrayList<SearchVideoEntity>) {
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: SearchVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchVideoEntity) = with(binding) {
            itemImgVideo.load(item.thumbnails)
            itemTxtName.text = item.title
            itemTxtUploadDate.text = item.uploadTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

}
