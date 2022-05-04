package com.example.recipes.data.datasources.cloud.commands

import okhttp3.OkHttpClient

interface Command<T> {
    fun execute(client: OkHttpClient, itemsCount: Int): T
}