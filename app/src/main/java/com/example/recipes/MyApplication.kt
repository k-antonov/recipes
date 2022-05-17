package com.example.recipes

import android.app.Application
import com.example.recipes.data.datasources.local.LocalDataSource
import okhttp3.OkHttpClient

class MyApplication : Application() {
    companion object {
        private lateinit var instance: MyApplication
        lateinit var localDataSource: LocalDataSource

        val client = OkHttpClient()

        fun get() = instance
    }

//    lateinit var localDataSource: LocalDataSource

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        localDataSource = LocalDataSource(this)
    }
}