package com.example.recipes.data.datasources.cloud.requesters

import okhttp3.OkHttpClient

interface Requester<T> {
    fun execute(client: OkHttpClient): T
}