package com.example.mediaapp.util

import android.app.Activity
import android.content.Context
import com.example.mediaapp.data.model.video.Item
import com.google.gson.GsonBuilder

class Util {
    /**
     * Shared Preferences에 아이템을 추가하는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @param item 추가하려는 아이템
     */
    fun addPrefItem(context: Context, item: Item) {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = GsonBuilder().create()
//editor.putString(item.snippet.thumbnails, gson.toJson(item)) 유틸을 dev푸시후 유틸에 함수 만들어놨으니 이 함수 써가지고 저장을 해주세요 내가 지금 필요한게 썸 네일이랑 타이틀인데 이 클래스에서 어떤걸 써야하는지도 알려달라
        //아니면 내가 쓰는 모델 클래스가 있으니 이거를 저장 시켜줘라sdsd

        editor.apply()
    }

    /**
     * Shared Preferences에서 특정 URL을 키로 하는 아이템을 제거하는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @param url 제거하려는 아이템의 URL
     */
    fun deletePrefItem(context: Context, url: String) {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(url)
        editor.apply()
    }

    /**
     * Shared Preferences에서 모든 북마크 아이템을 가져오는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @return 북마크된 아이템의 ArrayList
     */
    fun getPrefBookmarkItems(context: Context): ArrayList<SearchItemModel> {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val allEntries: Map<String, *> = prefs.all
        val bookmarkItems = ArrayList<SearchItemModel>()
        val gson = GsonBuilder().create()
        for ((key, value) in allEntries) {
            val item = gson.fromJson(value as String, SearchItemModel::class.java)
            bookmarkItems.add(item)
        }
        return bookmarkItems
    }
}