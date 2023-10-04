package com.example.mediaapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mediaapp.data.MypageContext
import com.example.mediaapp.databinding.MypageDialogModifyFragmentBinding
import java.io.File
import java.io.FileOutputStream

class MypageDialogModifyFragment : DialogFragment() {
    private var _binding: MypageDialogModifyFragmentBinding? = null
    private val binding get() = _binding!!

    interface OnDataModifiedListener {
        fun onDataModified()
    }

    private var onDataModifiedListener: OnDataModifiedListener? = null

    fun setOnDataModifiedListener(listener: OnDataModifiedListener) {
        onDataModifiedListener = listener

    }


    private val REQUEST_CODE_PICK_BACKGROUND_IMAGE = 1000

    private val REQUEST_CODE_PICK_PROFILE_IMAGE = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MypageDialogModifyFragmentBinding.inflate(inflater, container, false)

        // 0.SharedPreferences 초기화(버튼값저장하고하는거 시작)
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        //1.내가찜한영상친구에게공유하기
        val markerShare = binding.mypagemodifyBtnShare
       //2.쉐어프리퍼런스에서 기본값(고정)
        val isMarkerShareOn = sharedPreferences.getBoolean("isMarkerShareOn", false)
        // 버튼 상태 반전
        var updatedState = isMarkerShareOn


        //4.클릭리스너 설정
        markerShare.setOnClickListener {
            updatedState = !updatedState
            val editor = sharedPreferences.edit()
            // 현재 버튼 상태 저장
            editor.putBoolean("isMarkerShareOn", updatedState)
            // 변경사항 저장
            editor.apply()
            // 버튼 상태 변경
            setButtonState(markerShare, updatedState)
        }

        return binding.root

    }
    //5. 셋버튼스테이트 주기
    private fun setButtonState(button: AppCompatImageView, isOn: Boolean) {
        if (isOn) {
            // on 버튼일 때 이미지
            button.setImageResource(R.drawable.mypage_button_on)
        } else {
            // off 버튼일 때 이미지
            button.setImageResource(R.drawable.mypage_button_off)
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이름 글자 수 제한을 위한 텍스트 변경 감지
        binding.mypagemodifyEditName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.mypagemodifyTxtNamecount.text = "${s?.length ?: 0}/10"
            }
        })

        // 상태 글자 수 제한을 위한 텍스트 변경 감지
        binding.mypagemodifyEditStatus.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.mypagemodifyTxtStatuscount.text = "${s?.length ?: 0}/20"
            }
        })

        // 백그라운드 이미지 선택 버튼 클릭 리스너
        binding.mypagemodifyBtnBackgroundProfile.setOnClickListener {
            openImagePicker(REQUEST_CODE_PICK_BACKGROUND_IMAGE)
        }

        // 프로필 이미지 선택 버튼 클릭 리스너
        binding.mypagemodifyBtnProfile.setOnClickListener {
            openImagePicker(REQUEST_CODE_PICK_PROFILE_IMAGE)
        }

        // 수정 시, 이전 내용 보여주기
        val sharedPreferences =
            requireActivity().getSharedPreferences(MypageContext.PREF_MYPAGE, Context.MODE_PRIVATE)

        // 이전 이름 보여주기
        val previousName = sharedPreferences.getString(MypageContext.KEY_MYNAME, "")
        binding.mypagemodifyEditName.setText(previousName)

        // 이전 상태 메시지 보여주기
        val previousMemo = sharedPreferences.getString(MypageContext.KEY_MYSTATUS, "")
        binding.mypagemodifyEditStatus.setText(previousMemo)

        // "수정 완료" 버튼 클릭 리스너
        binding.mypagemodifyBtnSave.setOnClickListener {
            saveUserInfoToPreferences()
            dismiss()
        }
        loadToggleButton()
        loadImageAndSaveToPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openImagePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_BACKGROUND_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data

            uri?.let {
                val inputStream = requireActivity().contentResolver.openInputStream(uri)
                val targetFile =
                    File(requireActivity().filesDir, "backgroundImage.jpg") // 원하는 파일 이름으로 변경 가능
                val outputStream = FileOutputStream(targetFile)

                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                // 이미지 뷰에 선택된 이미지 설정
                binding.mypagemodifyImgBackgroundprofile.setImageURI(Uri.fromFile(targetFile))

                // 여기에 로깅 코드와 SharedPreferences 저장 코드를 추가
                val newImagePath = targetFile.absolutePath
                val sharedPreferences = requireActivity().getSharedPreferences(
                    MypageContext.PREF_MYPAGE,
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                editor.putString(MypageContext.KEY_MYIMAGE_BACK, newImagePath)
                editor.apply()


            }
        } else if (requestCode == REQUEST_CODE_PICK_BACKGROUND_IMAGE) {
            // 이미지 선택에 관한 기존 로직...
            if (resultCode == Activity.RESULT_OK) {
                binding.mypagemodifyImgBackgroundprofile.setImageURI(data?.data)  // 선택된 이미지를 img_myimage_modify에 설정
            } else {
                Toast.makeText(context, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 프로필사진
        if (requestCode == REQUEST_CODE_PICK_PROFILE_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data

            uri?.let {
                val inputStream = requireActivity().contentResolver.openInputStream(uri)
                val targetFile =
                    File(requireActivity().filesDir, "profileImage.jpg") // 원하는 파일 이름으로 변경 가능
                val outputStream = FileOutputStream(targetFile)

                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                // 이미지 뷰에 선택된 이미지 설정
                binding.mypagemodifyImgProfile.setImageURI(Uri.fromFile(targetFile))

                // 여기에 로깅 코드와 SharedPreferences 저장 코드를 추가
                val newImagePath = targetFile.absolutePath
                val sharedPreferences = requireActivity().getSharedPreferences(
                    MypageContext.PREF_MYPAGE,
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                editor.putString(MypageContext.KEY_MYIMAGE, newImagePath)
                editor.apply()

                Log.d("Mypage", "Saved Image Path: $newImagePath")


            }
        } else if (requestCode == REQUEST_CODE_PICK_PROFILE_IMAGE) {
            // 이미지 선택에 관한 기존 로직...
            if (resultCode == Activity.RESULT_OK) {
                binding.mypagemodifyImgProfile.setImageURI(data?.data)  // 선택된 이미지를 img_myimage_modify에 설정
            } else {
                Toast.makeText(context, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadToggleButton(){
        val sharedPreferences = requireActivity().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )
        //저장된값 불러오기(버튼)
        val toggleButton = sharedPreferences.getBoolean("isMarkerShareOn", false)
        setButtonState(binding.mypagemodifyBtnShare, toggleButton)
    }

    private fun loadImageAndSaveToPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            MypageContext.PREF_MYPAGE,
            Context.MODE_PRIVATE
        )



        val profileImagePath = sharedPreferences.getString(MypageContext.KEY_MYIMAGE, null)
        profileImagePath?.let {
            val profileImageFile = File(it)
            if (profileImageFile.exists()) {
                Glide.with(requireActivity())
                    .load(Uri.fromFile(profileImageFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.mypagemodifyImgProfile)

            } else {
                Log.e(
                    "ImageLoad",
                    "Profile image file does not exist at path: $profileImagePath"
                )
            }
        }
        val backgroundImagePath = sharedPreferences.getString(MypageContext.KEY_MYIMAGE_BACK, null)
        backgroundImagePath?.let {
            val profileImageFile = File(it)
            if (profileImageFile.exists()) {
                Glide.with(requireActivity())
                    .load(Uri.fromFile(profileImageFile))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.mypagemodifyImgBackgroundprofile)

            } else {
                Log.e(
                    "ImageLoad",
                    "Profile image file does not exist at path: $backgroundImagePath"
                )
            }
        }
    }

    private fun saveUserInfoToPreferences() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(MypageContext.PREF_MYPAGE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(MypageContext.KEY_MYNAME, binding.mypagemodifyEditName.text.toString())
        editor.putString(MypageContext.KEY_MYSTATUS, binding.mypagemodifyEditStatus.text.toString())
        editor.apply()
        onDataModifiedListener?.onDataModified()
    }
}