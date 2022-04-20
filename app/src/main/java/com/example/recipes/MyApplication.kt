package com.example.recipes

import android.app.Application
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.api.RecipeApiServiceImpl
import com.example.recipes.data.repository.RecipeRepository
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