package com.example.mediaapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaapp.data.model.video.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(
    private val searchRepository: VideoRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchYoutube(q:String) = viewModelScope.launch(Dispatchers.IO){
        try {
            val response:Response<SearchResponse> = searchRepository.search(Constants.API_KEY,"snippet",q,"mostPopular","ko-KR",10,"KR")
            if (response.isSuccessful){
                response.body()?.let { body ->
                    _searchResult.postValue(body)
                    Log.d("TAG", "searchYoutube: ${_searchResult.value}")
                }
            } else {
                Log.d("TAG", "fail : ViewModel - searchYotube fail")
            }
        }catch (e: Exception){
        }
    }
}