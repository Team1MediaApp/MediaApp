package com.example.mediaapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.data.MyDataModel
import com.example.mediaapp.databinding.MypageItemBinding
import com.example.mediaapp.databinding.MypageItemTitleBinding

class MypageItemAdapter(private val items: List<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SECTION_TITLE = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SECTION_TITLE -> {
                val titleBinding = MypageItemTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(titleBinding)
            }
            else -> {
                val itemBinding = MypageItemBinding.inflate(inflater, parent, false)
                ItemViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                // 섹션 타이틀을 바인딩합니다.
                holder.bindTitle(items[position] as String)
            }
            is ItemViewHolder -> {
                // 아이템을 바인딩합니다.
                holder.bindItem(items[position] as MyDataModel) // MyDataModel은 실제 데이터 모델 클래스로 대체해야 한다함. 일단 임시로 MyDataModel을 만들어 넣음
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item is String) {
            VIEW_TYPE_SECTION_TITLE
        } else {
            VIEW_TYPE_ITEM
        }
    }

    inner class TitleViewHolder(private val binding: MypageItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTitle(title: String) {
            binding.mypageItemMenuBar.text = title
            // 다른 타이틀 바인딩 작업 수행
        }
    }

    inner class ItemViewHolder(private val binding: MypageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: MyDataModel) {
            // 아이템제목
            binding.mypageItemMediaTitle.text = item.myTitle
            // 영상이미지뷰 uri에서 이미지설정!
            binding.mypageItemMadiaImg.setImageURI(Uri.parse(item.myThumbnailUrl))
        }
    }
}