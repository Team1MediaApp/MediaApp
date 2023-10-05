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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mediaapp.data.MypageContext
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.MypageFragmentBinding
import java.io.File
import com.example.mediaapp.util.Util

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
        val items = Util().getPrefBookmarkItems(requireContext())
        val adapter = MypageMyItemAdapter(requireContext(), items, object : OnItemClickListener{
            override fun onItemClick(item: Item) {
                    val detail = DetailFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable("Video_data", item)
                        }
                    }
                val transaction = parentFragmentManager.beginTransaction()
                //애니메이션 ▽ 적용내용 ▼ beginTranscaction자체는 애니메이션을 적용하는게 아님 // parentFragmentManager자체를 애니메이션화 하는 작업(빌더하는거)
                //아래는 애니를 어떻게 할건지 동작을 지정해주는 거
                transaction.setCustomAnimations(
                        R.anim.anim_right,
                        R.anim.anim_right_exit,
                        R.anim.anim_left,
                        R.anim.anim_left_exit
                    )
                //여기 밑에는 프레그먼트를 전환하는작업 (애니 > 원래대로)
                    transaction.replace(R.id.main_frame, detail)
                    transaction.addToBackStack(null)
                    transaction.commit() //commit의 의미는 수정작업을 마쳤다 !! 뭐 이런거임
                }
            }

        )
        MyRecy.adapter = adapter
        adapter.notifyDataSetChanged()

        return binding.root
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

//        아래 추가부문
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}