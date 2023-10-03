package com.example.mediaapp

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mediaapp.data.MypageContext
import com.example.mediaapp.databinding.MypageFragmentBinding
import java.io.File


class MypageFragment : Fragment(), MypageDialogModifyFragment.OnDataModifiedListener {
    private var _binding: MypageFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    // 멤버 변수 : sharedPreferences와 isBookmarkChannalOn 선언
    private lateinit var sharedPreferences: SharedPreferences
    private var isBookmarkChannalOn: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MypageFragmentBinding.inflate(inflater, container, false)


        //내가찜한채널 버튼(이미지뷰),수정버튼 변수설정
        val bookmarkChannalBtn = binding.mypageBtnBookmarkChannal
        // SharedPreferences에서 버튼 상태 불러오기, 기본값은 찜한채널 알림설정 on
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
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
            editor.commit()
            // 버튼 상태 변경
            setButtonState(bookmarkChannalBtn, isBookmarkChannalOn)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MypageFragmentBinding.bind(view)

        //저장된 이름 및 이미지 불러오는거
        updateData()


//        내 페이지 수정버튼(계속문제가 발생한 부분, show를 다이얼로그쪽에 선언해줬는데 이상시 확인필요)
        binding.mypageBtnModifyProfile.setOnClickListener {
            val mypageDialog = MypageDialogModifyFragment()
            mypageDialog.setOnDataModifiedListener(this)
            Log.d("aaaa","bbbb")
            mypageDialog.show(parentFragmentManager, "MypageDialogModifyFragment")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        updateData()
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
        editor.commit()
    }

    // ▽ 수정부분
    override fun onDataModified() {
        updateData()  // 데이터 수정 시에도 동일한 데이터 갱신 메소드 호출
    }


    private fun updateData() {
        // SharedPreferences에서 정보 가져오기
        val sharedPreferences =
            requireActivity().getSharedPreferences(MypageContext.PREF_MYPAGE, Context.MODE_PRIVATE)

        // 이름 가져와서 설정하기
        binding.mypageTxtName.text =
            sharedPreferences.getString(MypageContext.KEY_MYNAME, binding.mypageTxtName.text.toString())



        // 상태메세지 가져와서 설정하기
        binding.mypageTxtStatusMessage.text =
            sharedPreferences.getString(MypageContext.KEY_MYSTATUS, binding.mypageTxtStatusMessage.text.toString())


        // 이미지 경로 가져와서 Glide를 사용하여 이미지 로드하기
        val savedImagePath = sharedPreferences.getString(MypageContext.KEY_MYIMAGE, null)
        savedImagePath?.let {
            val imageFile = File(it)
            if (imageFile.exists()) {
                Glide.with(requireActivity())
                    .load(Uri.fromFile(imageFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.mypageImgProfile)
            }
        }
    }
}


