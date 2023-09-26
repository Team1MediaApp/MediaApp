package com.example.mediaapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaapp.data.model.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Query

class SearchViewModel(
    private val searchRepository: SearchRepository,
): ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

//    fun searchYoutube(query: String, maxResult:Int) = viewModelScope.launch(Dispatchers.IO){
//        try {
//            val response:Response<SearchResponse> = searchRepository.search(query,"snippet","any",maxResult,"video")
//            if (response.isSuccessful){
//                response.body()?.let { body ->
//                    _searchResult.postValue(body)
//                }
//            } else { }
//        }catch (e: Exception){
//        }
//    }
}