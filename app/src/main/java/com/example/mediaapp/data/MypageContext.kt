package com.example.mediaapp.data

import android.app.Application
import android.content.Context

class MypageContext : Application() {

    companion object {
        lateinit var globalContext: Context
        const val PREF_MYPAGE = "mypage_prefs"
        const val KEY_MYNAME = "myname"
        const val KEY_MYIMAGE = "key_myimage"
        const val KEY_MYIMAGE_BACK = "key_myimage_back"
        const val KEY_MYSTATUS = "mystatus"
    }

    override fun onCreate() {
        super.onCreate()
        globalContext = this
    }
}