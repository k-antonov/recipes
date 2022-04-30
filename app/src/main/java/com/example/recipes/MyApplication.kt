package com.example.recipes

import android.app.Application
import okhttp3.OkHttpClient

class MyApplication : Application() {
    companion object {
        private lateinit var instance: MyApplication
        val client = OkHttpClient()

        fun get() = instance
    }

    init {
        instance = this
    }
}