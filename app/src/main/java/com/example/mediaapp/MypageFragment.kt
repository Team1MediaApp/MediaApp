package com.example.mediaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.example.mediaapp.databinding.MypageFragmentBinding


class MypageFragment : Fragment() {
    private var _binding: MypageFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    // 멤버 변수 : sharedPreferences와 isBookmarkChannalOn 선언
    private val sharedPreferences: SharedPreferences by lazy {  // SharedPreferences에서 버튼 상태 불러오기, 기본값은 찜한채널 알림설정 on
        requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }
    private var isBookmarkChannalOn: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MypageFragmentBinding.inflate(inflater, container, false)


        //내가찜한채널 버튼(이미지뷰) 변수설정
        val bookmarkChannalBtn = binding.mypageBtnBookmarkChannal

        // 로드 기본값 ON
        var isBookmarkChannalOn = sharedPreferences.getBoolean("isBookmarkChannalOn", true)

        // 버튼 초기 상태 설정
        setButtonState(bookmarkChannalBtn, isBookmarkChannalOn)

        bookmarkChannalBtn.setOnClickListener {
            val editor = sharedPreferences.edit()

            // 버튼 상태 반전
            isBookmarkChannalOn = !isBookmarkChannalOn

            // 현재 버튼 저장
            editor.putBoolean("isBookmarkChannalOn", isBookmarkChannalOn)

            //저장이 잘 안될때는 apply()대신 commit()을 써보라고 함.(에뮬 종료후 다시켰을때)
            editor.apply()

            // 버튼 상태 변경
            setButtonState(bookmarkChannalBtn, isBookmarkChannalOn)

        }

        return binding.root

    }


    private fun setButtonState(button: AppCompatImageView, isOn: Boolean) {
        if (isOn) {
            // on 버튼일 때 이미지
            button.setImageResource(R.drawable.mypage_button_on)
        } else {
            // off 버튼일 때 이미지
            button.setImageResource(R.drawable.mypage_button_off)
        }
    }

    override fun onStop() {
        super.onStop()
        val editor = sharedPreferences.edit()
        editor.putBoolean("isBookmarkChannalOn", isBookmarkChannalOn)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

