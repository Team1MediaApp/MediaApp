package com.example.mediaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.example.mediaapp.data.api.SearchRepositoryImpl
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.data.model.video.Thumbnails
import com.example.mediaapp.databinding.DetailFragmentBinding
import com.example.mediaapp.model.SearchVideoEntity
import com.example.mediaapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private var url: String? = null

    private var islike = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
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
        shareButton()
        val item = arguments?.getSerializable("Video_data") as Item?
        item?.let {
            with(binding) {
                detailTxtChannel.text = it.snippet.channelTitle
                detailTxtVideoTitle.text = it.snippet.title
                detailImgThumnail.load(it.snippet.thumbnails.medium.url)
                detailTxtVideoDetail.text = it.snippet.description
            }
            getChannelData(it.snippet.channelId)
            url = it.id
        }

        // 뒤로가기 버튼 구현 및 화면 전환 시 애니메이션
        binding.detailBtnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 좋아요 버튼 구현, sharedPreference
        // 키워드 : 직렬화
        // 1. sharedPr에서 저장되어있는 값을 가져와서 json형태로 변경 후 data를 넣어준다. (json object 필수... 공부해)
        // 2. 꺼내 올 때는 sharedPr에서 저장되어있는 값을 Gson을 사용하여 원하는 객체 리스트로 변경하여 사용한다.
        if (item != null) {
            islike = Util().BookmarkCheck(requireContext(), item.snippet.title)
            if (islike) {
                binding.detailImgBookmark.setImageResource(R.drawable.detail_img_full_bookmark)
            } else {
                binding.detailImgBookmark.setImageResource(R.drawable.detail_img_line_bookmark)

            }
        }
        binding.detailBtnLike.setOnClickListener {
            item?.let {

                if (islike) {
                    binding.detailImgBookmark.setImageResource(R.drawable.detail_img_line_bookmark)
                    Util().deletePrefItem(requireContext(), it)
                    islike = false
                    Toast.makeText(context, "북마크 취소!", Toast.LENGTH_SHORT).show()
//                    Log.d("BookmarkLog", "북마크 취소됨: ${it.snippet.title}")
                } else {
                    binding.detailImgBookmark.setImageResource(R.drawable.detail_img_full_bookmark)
                    Util().addPrefItem(requireContext(), it)
                    islike = true
                    Toast.makeText(context, "북마크 추가!", Toast.LENGTH_SHORT).show()
//                    Log.d("BookmarkLog", "북마크 추가됨: ${it.snippet.title}")
                }
            }
        }
    }

    private fun getChannelData(channelId: String) {
        GlobalScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val channel = SearchRepositoryImpl().getSearchChannel(channelId)
                withContext(Dispatchers.Main) {
                    binding.detailImgChannel.load(channel.items?.get(0)?.snippet?.thumbnails?.medium?.url)
                    binding.detailTxtChannel.text = channel.items?.get(0)?.snippet?.title
                    binding.detailTxtChannelSub.text =
                        channel.items?.get(0)?.statistics?.subscriberCount
                }
            }.onFailure {
                Log.d("network", "response failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


// snippet.thumbnails.(key), statistics.subscriberCount