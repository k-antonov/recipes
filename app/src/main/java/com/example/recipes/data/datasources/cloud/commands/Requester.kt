package com.example.recipes.data.datasources.cloud.commands

import okhttp3.OkHttpClient

interface Requester<T> {
    fun execute(client: OkHttpClient): T
}