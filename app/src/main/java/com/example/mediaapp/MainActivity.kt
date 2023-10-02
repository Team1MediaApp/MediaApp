package com.example.mediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mediaapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchRepository = VideoRepositoryImpl()
        val factory = SearchViewModelProviderFactory(searchRepository,this)
        searchViewModel = ViewModelProvider(this,factory)[SearchViewModel::class.java]

//        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, MypageFragment()).commit()



    }
}