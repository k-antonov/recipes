package com.example.recipes

import android.app.Application

class MyApplication : Application() {
    companion object {
        private lateinit var instance: MyApplication

        fun get() = instance
    }

    init {
        instance = this
    }
}