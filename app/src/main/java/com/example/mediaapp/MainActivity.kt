package com.example.mediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.mediaapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var currentFragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val myPageFragment = MypageFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        currentFragment = homeFragment

        val searchRepository = VideoRepositoryImpl()
        val factory = SearchViewModelProviderFactory(searchRepository, this)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()

        val bottomNav = binding.mainNav
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.title) {
                "Home" -> {
                    switchFragment(homeFragment)
                    true
                }

                "Search" -> {
                    switchFragment(searchFragment)
                    true
                }

                "MyPage" -> {
                    switchFragment(myPageFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        if (fragment != currentFragment) {
            fragmentManager.beginTransaction().replace(R.id.main_frame, fragment).commit()
            currentFragment = fragment
        }
    }
}