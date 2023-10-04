package com.example.mediaapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mediaapp.data.MypageContext
import com.example.mediaapp.databinding.MypageFragmentBinding
import java.io.File
import com.example.mediaapp.data.MyDataModel

class MypageFragment : Fragment(), MypageDialogModifyFragment.OnDataModifiedListener {
    private var _binding: MypageFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MypageFragmentBinding.inflate(inflater, container, false)

        // SharedPreferences 초기화
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        //내가찜한채널 버튼(이미지뷰),수정버튼 변수설정
        val bookmarkChannalBtn = binding.mypageBtnBookmarkChannal

        // SharedPreferences에서 버튼 상태 불러오기, 기본값은 찜한채널 알림설정 on
        //반전하는 변수는 밖으로 빼줄것
        val isBookmarkChannalOn = sharedPreferences.getBoolean("isBookmarkChannalOn", false)
        var updatedState = isBookmarkChannalOn



        bookmarkChannalBtn.setOnClickListener {
            // 버튼 상태 반전
            updatedState = !updatedState
            val editor = sharedPreferences.edit()
            editor.putBoolean("isBookmarkChannalOn", updatedState)
            // 변경사항 저장
            editor.apply()
            // 버튼 상태 변경
            setButtonState(bookmarkChannalBtn, updatedState)
        }

        //수정 버튼 클릭 리스너 설정
        binding.mypageBtnModifyProfile.setOnClickListener {
            val mypageDialog = MypageDialogModifyFragment()
            mypageDialog.setOnDataModifiedListener(this)
            mypageDialog.show(parentFragmentManager, "MypageDialogModifyFragment")
        }
        updateData()
        loadToggleButton()


        //★★★★★리사이클러뷰 선언
        val MyRecy = binding.mypageMyRecyclerview
        val FriendRecy = binding.mypageFrindRecyclerview
        //★★★★★ 중첩된 리사이클러 뷰 어댑터를 생성하고 설정
        val items = generateSampleData()
        val adapter_My = MypageMyItemAdapter(requireContext(), items.toMutableList())
        val adapter_Friend = MypageMyItemAdapter(requireContext(),items.toMutableList())
        MyRecy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        FriendRecy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        MyRecy.adapter = adapter_My
        FriendRecy.adapter = adapter_Friend

        return binding.root

    }

    //★★★★★ 여기 아래 데이터 받아오면 넣으면 됨 ( 디테일 - 마이페이지)
    private fun generateSampleData(): List<MyDataModel> {
        val items = mutableListOf<MyDataModel>()


        // 아이템 추가 예시
        val videoItem = MyDataModel("영상 제목",  "https://cdn.aitimes.kr/news/photo/202303/27617_41603_044.jpg")
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)
        items.add(videoItem)

        // 다른 섹션과 아이템을 추가 (필요한 만큼 반복할 수 있음!)

        return items
    }



    override fun onDataModified() {
        updateData()  // 데이터 수정 시에도 동일한 데이터 갱신 메소드 호출
    }

    private fun updateData() {
        // SharedPreferences에서 정보 가져오기
        val sharedPreferences = requireActivity().getSharedPreferences(
            MypageContext.PREF_MYPAGE,
            Context.MODE_PRIVATE
        )

        // 이름 가져와서 설정하기
        binding.mypageTxtName.text =
            sharedPreferences.getString(
                MypageContext.KEY_MYNAME,
                binding.mypageTxtName.text.toString()
            )

        // 상태메세지 가져와서 설정하기
        binding.mypageTxtStatusMessage.text =
            sharedPreferences.getString(
                MypageContext.KEY_MYSTATUS,
                binding.mypageTxtStatusMessage.text.toString()
            )

        val backgroundImagePath = sharedPreferences.getString(MypageContext.KEY_MYIMAGE_BACK, "")
        backgroundImagePath?.let {
            val imageFile = File(it)
            if (imageFile.exists()) {
                Glide.with(requireActivity())
                    .load(Uri.fromFile(imageFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.mypageImgBackgroundprofile)
            }
        }

        // 프로필 이미지 경로 가져와서 Glide를 사용하여 이미지 로드하기
        val profileImagePath = sharedPreferences.getString(MypageContext.KEY_MYIMAGE, "")
        profileImagePath?.let {
            val profileImageFile = File(it)
            if (profileImageFile.exists()) {
                Glide.with(requireActivity())
                    .load(Uri.fromFile(profileImageFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.mypageImgProfile)
            } else {
                Log.e(
                    "ImageLoad",
                    "Profile image file does not exist at path: $profileImagePath"
                )
            }
        }

        //아래 추가부문
        val profileImageFile = File(profileImagePath)
        if (profileImageFile.exists()) {
            Glide.with(requireActivity())
                .load(Uri.fromFile(profileImageFile))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.mypageImgProfile)
        } else {
            Log.e("ImageLoad", "Profile image file does not exist at path: $profileImagePath")
        }

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

    private fun loadToggleButton() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )
        //저장된값 불러오기(버튼)
        val toggleButton = sharedPreferences.getBoolean("isMarkerShareOn", false)
        setButtonState(binding.mypageBtnBookmarkChannal, toggleButton)
    }




}