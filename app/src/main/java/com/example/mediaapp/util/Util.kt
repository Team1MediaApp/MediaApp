package com.example.mediaapp.util

import android.app.Activity
import android.content.Context
import com.example.mediaapp.data.model.video.Item
import com.google.gson.GsonBuilder

class Util {

fun addPrefItem(context: Context, item: Item) {
    val prefs = context.getSharedPreferences("videoID", Activity.MODE_PRIVATE)
    val editor = prefs.edit()
    val gson = GsonBuilder().create()
    editor.putString(item.snippet.title, gson.toJson(item))
    editor.putString(item.snippet.thumbnails.medium.url, gson.toJson(item))
        editor.apply()
}

    fun deletePrefItem(context: Context, item: Item) {
        val prefs = context.getSharedPreferences("videoID", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(item.snippet.title)
        editor.remove(item.snippet.thumbnails.medium.url)
        editor.apply()
    }

    fun getPrefBookmarkItems(context: Context): ArrayList<Item> {
        val prefs = context.getSharedPreferences("videoID", Activity.MODE_PRIVATE)
        val allEntries: Map<String, *> = prefs.all
        val bookmarkItems = ArrayList<Item>()
        val gson = GsonBuilder().create()
        for ((key, value) in allEntries) {
            val item = gson.fromJson(value as String, Item::class.java)
            bookmarkItems.add(item)
        }
        return bookmarkItems
    }

    // 좋아요 눌린 건지 아닌지 판단(기본적인 부분)
    fun BookmarkCheck(context:Context, title:String): Boolean {
        val prefs = context.getSharedPreferences("videoID", Activity.MODE_PRIVATE)
        return prefs.contains(title)
    }
}