package com.example.mediaapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
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

    private var isUserScrolling = false

    private var scrollHandler = Handler(Looper.getMainLooper())
    private val scrollRunnable = object : Runnable {
        override fun run() {
            autoScroll()
            scrollHandler.postDelayed(this, 5000)
        }
    }
    private var categoryQuery: String? = null

    private var _pageToken: String? = null
    private val pageToken get() = _pageToken!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel = (activity as MainActivity).searchViewModel

        setupRecyclerView()
        searchCategory()
        searchViewModel.searchYoutube("1", "")
        searchViewModel.searchChannels("tv")
        searchViewModel.searchTrending()


        searchViewModel.trendingResult.observe(viewLifecycleOwner) { response ->
            val result: List<Item> = response.items

            homeTrendingRcvViewAdapter.submitList(result)
        }

        searchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val result: List<Item> = response.items
            _pageToken = response.nextPageToken

            homeCategoryRcvViewAdapter.submitList(result)
            homeCategoryRcvViewAdapter.notifyDataSetChanged()
        }

        searchViewModel.channelResult.observe(viewLifecycleOwner) { response ->
            val result: List<ChItem> = response.chItems

            homeChannelRcvAdapter.submitList(result)
        }

        scrollHandler.postDelayed(scrollRunnable, 5000)
    }

    private fun searchCategory() {

        binding.homeSpnCategorySelect.setOnSpinnerItemSelectedListener<String> { _, _, _, query ->
            searchViewModel.searchYoutube(CategoryId.categoryMap[query] ?: "1", "")
            searchViewModel.searchChannels(query)

            categoryQuery = query
        }
    }


    private fun setupRecyclerView() {
        val trendingSnapHelper = LinearSnapHelper()
        trendingSnapHelper.attachToRecyclerView(binding.homeRcvTrendingList)
        homeCategoryRcvViewAdapter = HomeCategoryRcvViewAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.anim_right,
                    R.anim.anim_right_exit,
                    R.anim.anim_left,
                    R.anim.anim_left_exit
                )
                transaction.add(R.id.main_frame, detail)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        binding.homeRcvCategoryList.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeCategoryRcvViewAdapter
            addOnScrollListener(categoryRcvListener)
        }

        homeTrendingRcvViewAdapter = HomeTrendingRcvAdapter(object : ItemClick {
            override fun onClick(item: Item) {
                val detail = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("Video_data", item)
                    }
                }
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.anim_right,
                    R.anim.anim_right_exit,
                    R.anim.anim_left,
                    R.anim.anim_left_exit
                )
                transaction.add(R.id.main_frame, detail)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        binding.homeRcvTrendingList.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeTrendingRcvViewAdapter
            addOnScrollListener(trendingRcvListener)
        }

        homeChannelRcvAdapter = HomeChannelRcvAdapter()

        binding.homeRcvChannelList.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeChannelRcvAdapter
        }
    }

    private val trendingRcvListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    isUserScrolling = true
                }

                RecyclerView.SCROLL_STATE_IDLE -> {
                    if (isUserScrolling) {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val itemCount = recyclerView.adapter?.itemCount ?: 0

                        if (lastVisibleItemPosition == itemCount - 1) {
                            recyclerView.scrollToPosition(0)
                        }
                    }
                    isUserScrolling = false
                }
            }
        }
    }

    private val categoryRcvListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    isUserScrolling = true
                }

                RecyclerView.SCROLL_STATE_IDLE -> {
                    if (isUserScrolling) {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        val itemCount = recyclerView.adapter?.itemCount ?: 0

                        if (lastVisibleItemPosition == itemCount - 1) {
                            searchViewModel.searchYoutubeNextPage(
                                CategoryId.categoryMap[categoryQuery] ?: "1", pageToken
                            )
                        }
                    }
                    isUserScrolling = false
                }
            }
        }
    }

    private fun autoScroll() {
        val layoutManager = binding.homeRcvTrendingList.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = homeTrendingRcvViewAdapter.itemCount

        if (lastVisibleItemPosition < itemCount - 1) {
            binding.homeRcvTrendingList.smoothScrollToPosition(lastVisibleItemPosition + 1)
        } else {
            binding.homeRcvTrendingList.scrollToPosition(0)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.homeSpnCategorySelect.dismiss()
        Log.d("xxxx", "Home pause : ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollHandler.removeCallbacksAndMessages(null)
        _binding = null
        Log.d("xxxx", "Home Destroyed: ")
    }
}