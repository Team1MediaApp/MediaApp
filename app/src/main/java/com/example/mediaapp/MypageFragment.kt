package com.example.mediaapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediaapp.databinding.MypageDialogModifyFragmentBinding
import com.example.mediaapp.databinding.MypageFragmentBinding


class MypageFragment : Fragment() {
    private var _binding: MypageFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MypageFragmentBinding.inflate(inflater, container, false)


        //내가찜한채널 버튼(이미지뷰) 변수설정
        val bookmarkChannalBtn = binding.mypageBtnBookmarkChannal

        // SharedPreferences에서 버튼 상태 불러오기, 기본값은 true
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isBookmarkChannalOn = sharedPreferences.getBoolean("isButtonOn", true)

        bookmarkChannalBtn.setOnClickListener {
            if (isBookmarkChannalOn){
                //on버튼일 때 이미지
                bookmarkChannalBtn.setImageResource(R.drawable.mypage_button_on)
            } else {
                //off일때
                bookmarkChannalBtn.setImageResource(R.drawable.mypage_button_off)
            }

            // 버튼 상태 반전
            val editor = sharedPreferences.edit()
            editor.putBoolean("isBookmarkChannalOn", !isBookmarkChannalOn)
            editor.apply()
        }

        return binding.root
    }
}

