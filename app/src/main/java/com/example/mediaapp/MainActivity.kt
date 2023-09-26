package com.example.mediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediaapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}