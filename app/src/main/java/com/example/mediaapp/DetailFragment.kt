package com.example.mediaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // shareButton()
    }


 //공유하기 버튼 기능 구현
    private fun shareButton(){
        val shareButton = binding.detailBtnShare
        shareButton.setOnClickListener {
            val shareIntent = Intent().apply() {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "youtube.com") // <- 전달되고자 하는 URL부분
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("jina", "DetailFragment: arguments")
        val item = arguments?.getSerializable("Video_data") as? Item
        item?.let {
            with(binding){
                detailTxtChannel.text = it.snippet.channelTitle
                detailTxtVideoTitle.text = it.snippet.title
                detailImgThumnail.load(it.snippet.thumbnails.medium.url)
                detailTxtVideoDetail.text = it.snippet.description
            }
        }
    }
}
