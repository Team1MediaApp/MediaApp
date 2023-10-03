package com.example.mediaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.data.api.SearchRepositoryImpl
import com.example.mediaapp.databinding.SearchFragmentBinding
import com.example.mediaapp.model.SearchVideoEntity
import com.example.mediaapp.ui.adapter.SearchChannelListAdapter
import com.example.mediaapp.ui.adapter.SearchVideoListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val videoListAdapter by lazy {
        SearchVideoListAdapter()
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
        initView()
    }

    private fun initView() = with(binding) {
        searchSearchViewSearch.isSubmitButtonEnabled = true
        searchListviewVideo.adapter = videoListAdapter
        searchListviewVideo.layoutManager = LinearLayoutManager(context)
//        searchListviewChannel.adapter = SearchChannelListAdapter()
        searchListviewResult.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!v.canScrollVertically(1)) {
                Log.i("test", "BOTTOM SCROLL");
            }
//            if (scrollY == (v.measuredHeight - v.getChildAt(0).measuredHeight)) {
//            }
        })
//        searchListviewVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val lastItemPosition =
//                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
//                if (!searchListviewVideo.canScrollVertically(1) && lastItemPosition == videoListAdapter.itemCount - 1) {
//                    Log.d("test", "last")
//                }
//            }
//
//        })
        searchSearchViewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    videoListAdapter.refreshList()
                    getData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getData(query: String) {
        val videoData: ArrayList<SearchVideoEntity> = arrayListOf()
        GlobalScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val videos = SearchRepositoryImpl().getSearchImage(query, "video", 10)
                val channels = SearchRepositoryImpl().getSearchImage(query, "channel", 1)
                withContext(Dispatchers.Main) {
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
                }
            }.onFailure {
                Log.d("network", "response failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {}
}