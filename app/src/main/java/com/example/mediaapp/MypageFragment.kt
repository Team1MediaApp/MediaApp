package com.example.mediaapp

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

        return binding.root
    }
}


//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MypageFragment().apply {
//                arguments = Bundle().apply {
//
//                }
//            }
//    }
//}