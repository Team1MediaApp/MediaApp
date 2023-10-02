package com.example.mediaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaapp.data.model.channel.ChItem
import com.example.mediaapp.util.CategoryId
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.HomeFragmentBinding
import com.example.mediaapp.ui.adapter.HomeCategoryRcvViewAdapter
import com.example.mediaapp.ui.adapter.HomeChannelRcvAdapter
import com.example.mediaapp.ui.adapter.HomeTrendingRcvAdapter


class HomeFragment : Fragment() {

    private var _binding:HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeCategoryRcvViewAdapter: HomeCategoryRcvViewAdapter
    private lateinit var homeTrendingRcvViewAdapter: HomeTrendingRcvAdapter
    private lateinit var homeChannelRcvAdapter: HomeChannelRcvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).searchViewModel


        setupRecyclerView()
        searchCategory()
        searchViewModel.searchYoutube("1")
        searchViewModel.searchChannels("tv")
        searchViewModel.searchTrending()
        searchViewModel.trendingResult.observe(viewLifecycleOwner){ response ->
            val result:List<Item> = response.items

            homeTrendingRcvViewAdapter.submitList(result)
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner){ response ->
            val result:List<Item> = response.items

            homeCategoryRcvViewAdapter.submitList(result)
            Log.d("TAG", "submitList? : ${homeCategoryRcvViewAdapter.submitList(result)}")

        }

        searchViewModel.channelResult.observe(viewLifecycleOwner){ response ->
            val result:List<ChItem> = response.chItems

            homeChannelRcvAdapter.submitList(result)
        }
    }

    private fun searchCategory() {

        binding.homeSpnCategorySelect.setOnSpinnerItemSelectedListener<String> { _, _, _, query ->
            searchViewModel.searchYoutube(CategoryId.categoryMap[query] ?: "1")
            searchViewModel.searchChannels(query)
        }
    }


    private fun setupRecyclerView(){
        homeCategoryRcvViewAdapter = HomeCategoryRcvViewAdapter()
        binding.homeRcvCategoryList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = homeCategoryRcvViewAdapter
        }
        homeTrendingRcvViewAdapter = HomeTrendingRcvAdapter()
        binding.homeRcvTrendingList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = homeTrendingRcvViewAdapter
        }
        homeChannelRcvAdapter = HomeChannelRcvAdapter()
        binding.homeRcvChannelList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = homeChannelRcvAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}