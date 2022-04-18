package com.example.recipes.data.api

import okhttp3.OkHttpClient

interface Command<T> {
    fun execute(client: OkHttpClient): T
}