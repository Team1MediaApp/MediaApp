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
import com.example.mediaapp.ui.adapter.ChannelItemClick
import com.example.mediaapp.ui.adapter.HomeCategoryRcvViewAdapter
import com.example.mediaapp.ui.adapter.HomeChannelRcvAdapter
import com.example.mediaapp.ui.adapter.HomeTrendingRcvAdapter
import com.example.mediaapp.ui.adapter.ItemClick


class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var homeCategoryRcvViewAdapter: HomeCategoryRcvViewAdapter
    private lateinit var homeTrendingRcvViewAdapter: HomeTrendingRcvAdapter
    private lateinit var homeChannelRcvAdapter: HomeChannelRcvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).searchViewModel


        setupRecyclerView()
        searchImages()
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

    private fun searchImages() {

        binding.searchFragBtnSearch.setOnClickListener {
            val searchBar = binding.searchFragEditSearch
            if (searchBar.text.isNotEmpty()) {
                searchBar.text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        searchViewModel.searchYoutube(query)
                        searchViewModel.searchTrending()
                        searchViewModel.searchChannels(CategoryId.categoryMap[query] ?: "sport")
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        homeCategoryRcvViewAdapter = HomeCategoryRcvViewAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.homeRcvCategoryList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeCategoryRcvViewAdapter
        }

        homeTrendingRcvViewAdapter = HomeTrendingRcvAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.homeRcvTrendingList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeTrendingRcvViewAdapter
        }

        homeChannelRcvAdapter = HomeChannelRcvAdapter(object : ChannelItemClick {
            override fun onClick(item: ChItem) {
                Log.d("jina", "homeChannelAdapter: onClick $item")
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, detail)
                    .addToBackStack(null)
                    .commit()
            }
        })

        binding.homeRcvChannelList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeChannelRcvAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}