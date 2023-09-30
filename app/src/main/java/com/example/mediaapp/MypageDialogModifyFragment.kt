package com.example.mediaapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediaapp.databinding.MypageDialogModifyFragmentBinding


class  MypageDialogModifyFragment : Fragment() {
    private var _binding: MypageDialogModifyFragmentBinding? = null
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
        _binding = MypageDialogModifyFragmentBinding.inflate(inflater, container, false)

//▽이름글자 수 제한을위해 몇글자 썼는지 표시하기
        val mypage10limittNameReader = binding.mypagemodifyEditName // 이름을 쓰는칸인데, 10자제한해야해서 몇자까지 썼는지 읽어오기 위해 불렀다.
        val mypage10limitName = binding.mypagemodifyTxtNamecount // 10자 제한을 표시하는 txt이다.


        mypage10limittNameReader.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mypage10limitName.text = "${s?.length ?: 0}/10"
            }
        })

        //▽상태글자 수 제한을위해 몇글자 썼는지 표시하기
        val mypage20limitStatusReader = binding.mypagemodifyEditStatus// 이름을 쓰는칸인데, 10자제한해야해서 몇자까지 썼는지 읽어오기 위해 불렀다.
        val mypage20limitStatus = binding.mypagemodifyTxtStatuscount // 10자 제한을 표시하는 txt이다.


        mypage20limitStatusReader.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mypage20limitStatus.text = "${s?.length ?: 0}/20"
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//companion object {
//
//    @JvmStatic
//    fun newInstance(param1: String, param2: String) =
//        Mypage_dialog_modify_Fragment().apply {
//            arguments = Bundle().apply {
//                putString(ARG_PARAM1, param1)
//                putString(ARG_PARAM2, param2)
//            }
//        }
//}
