package com.example.mediaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.data.model.video.Thumbnails
import com.example.mediaapp.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private var url: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        shareButton()
        return binding.root
    }

    // 공유하기 버튼 구현, onCreateView에 있는 shareButton() 포함
    private fun shareButton() {
        binding.detailBtnShare.setOnClickListener {
            val shareIntent = Intent().apply() {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=$url"
                ) // <- 공유할 URL 적기, 파이어베이스 - 다이나믹 링크
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "null"))
        }
    }

    // 정보 받아오는 부분
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getSerializable("Video_data") as Item?
        item?.let {
            with(binding) {
                detailTxtChannel.text = it.snippet.channelTitle
                detailTxtVideoTitle.text = it.snippet.title
                detailImgThumnail.load(it.snippet.thumbnails.medium.url)
                detailTxtVideoDetail.text = it.snippet.description
            }
            url = it.id
        }

        // 뒤로가기 버튼 구현 및 화면 전환 시 애니메이션
        binding.detailBtnBack.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.anim_left,
                R.anim.anim_left_exit
            )

            transaction.replace(R.id.detail_framelayout, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // 좋아요 버튼 구현, sharedPreference
        binding.detailBtnLike.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences(
                "pref",
                0  // 0이 Context.MODE_PRIVATE 역할을 한다.
            )
            val editor = sharedPreferences.edit()
            val like = sharedPreferences.getBoolean("video_liked", false)

            if (!like) {
                editor.putBoolean("video_liked", true)
                item?.let {
                    editor.putString("pref_video_title", it.snippet.title)
                    editor.putString("pref_video_thumnail", it.snippet.thumbnails.medium.url)
                }
            } else {
                editor.putBoolean("video_liked", false)
                editor.remove("pref_video_title")
                editor.remove("pref_video_thumnail")
            }
            editor.apply()
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }