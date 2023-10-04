package com.example.mediaapp

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaapp.data.api.SearchRepositoryImpl
import com.example.mediaapp.databinding.SearchFragmentBinding
import com.example.mediaapp.model.SearchChannelEntity
import com.example.mediaapp.model.SearchVideoEntity
import com.example.mediaapp.ui.adapter.SearchChannelListAdapter
import com.example.mediaapp.ui.adapter.SearchHistoryLIstAdapter
import com.example.mediaapp.ui.adapter.SearchVideoListAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private var nextPageToken: String? = null
    private var userQuery: ArrayList<String> = arrayListOf()
    private var nextProgress: Boolean = true
    private val videoListAdapter by lazy {
        SearchVideoListAdapter()
    }
    private val channelListAdapter by lazy {
        SearchChannelListAdapter()
    }
    private val historyListAdapter by lazy {
        SearchHistoryLIstAdapter()
    }
    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            SEARCH_PREF, Context.MODE_PRIVATE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initData() {
        userQuery = getSharedData() ?: arrayListOf()
    }

    private fun initView() = with(binding) {
        searchSearchViewSearch.isSubmitButtonEnabled = true
        searchListviewVideo.adapter = videoListAdapter
        searchListviewVideo.layoutManager = LinearLayoutManager(context)
        searchListviewChannel.adapter = channelListAdapter
        searchListviewChannel.layoutManager = LinearLayoutManager(context)
        searchRecentQueryList.adapter = historyListAdapter
        searchRecentQueryList.layoutManager = LinearLayoutManager(context)
        searchListviewResult.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val totalHeight = searchListviewResult.getChildAt(0).height
            val scrolledHeight = scrollY + searchListviewResult.height
            if (scrolledHeight >= totalHeight && nextProgress) {
                getMoreData()
            }
        })
        searchSearchViewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    videoListAdapter.refreshList()
                    channelListAdapter.refreshList()
                    getData(query)
                    userQuery.add(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = root.height
            val keypadHeight = rect.bottom - screenHeight
            if (keypadHeight > (screenHeight * 0.25)) {
                historyListAdapter.updateData(userQuery)
                searchRecentQueryList.visibility = View.VISIBLE
            } else {
                searchRecentQueryList.visibility = View.GONE
            }
        }
//        dispatchTouchEvent
    }

    private fun getMoreData() {
        nextProgress = false
        if (nextPageToken.isNullOrBlank() && userQuery.isEmpty()) return
        val videoData: ArrayList<SearchVideoEntity> = arrayListOf()
        binding.searchProgressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val videos = SearchRepositoryImpl().getSearchImageByPageToken(
                    nextPageToken!!, 10, userQuery[userQuery.size - 1]
                )
                withContext(Dispatchers.Main) {
                    delay(1000)
                    nextPageToken = videos.nextPageToken.toString()
                    nextProgress = true
                    videos.items?.forEach { it ->
                        videoData.add(
                            SearchVideoEntity(
                                it.snippet?.channelTitle.toString(),
                                it.snippet?.title.toString(),
                                it.snippet?.thumbnails?.medium?.url.toString(),
                                it.snippet?.publishedAt.toString(),
                            )
                        )
                    }
                    videoListAdapter.addDataList(videoData)
                    binding.searchProgressBar.visibility = View.GONE
                    nextProgress = true
                }
            }.onFailure {
                Log.d("network", "response failed")
            }
        }
    }

    private fun getData(query: String) {
        val videoData: ArrayList<SearchVideoEntity> = arrayListOf()
        val channelData: ArrayList<SearchChannelEntity> = arrayListOf()
        GlobalScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val videos = SearchRepositoryImpl().getSearchImage(query, "video", 10)
                val channels = SearchRepositoryImpl().getSearchImage(query, "channel", 1)
                withContext(Dispatchers.Main) {
                    nextPageToken = videos.nextPageToken.toString()
                    nextProgress = true
                    videos.items?.forEach { it ->
                        videoData.add(
                            SearchVideoEntity(
                                it.snippet?.channelTitle.toString(),
                                it.snippet?.title.toString(),
                                it.snippet?.thumbnails?.medium?.url.toString(),
                                it.snippet?.publishedAt.toString(),
                            )
                        )
                    }
                    channels.items?.forEach { it ->
                        channelData.add(
                            SearchChannelEntity(
                                it.snippet?.channelTitle.toString(),
                                it.snippet?.description.toString(),
                                it.snippet?.thumbnails?.medium?.url.toString(),
                            )
                        )
                    }
                    channelListAdapter.addDataList(channelData)
                    videoListAdapter.addDataList(videoData)
                }
            }.onFailure {
                Log.d("network", "response failed")
            }
        }
    }

    private fun updateSharedData(data: ArrayList<String>) {
        if (data.isEmpty()) return
        val gson = Gson()
        val json = gson.toJson(data)
        val editor = sharedPreferences.edit()
//        editor.remove(SEARCH_PREF_KEY)
        editor.putString(SEARCH_PREF_KEY, json)
        editor.apply()
    }

    private fun getSharedData(): ArrayList<String>? {
        val json = sharedPreferences.getString(SEARCH_PREF_KEY, "")
        val gson = Gson()
        val arrayListType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, arrayListType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateSharedData(userQuery)
        _binding = null
    }

    companion object {
        private const val SEARCH_PREF = "search_pref"
        private const val SEARCH_PREF_KEY = "searchQueryList"
    }
}