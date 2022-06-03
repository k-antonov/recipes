package com.example.recipes.data.categories.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseRemoteRequester
import com.squareup.moshi.JsonAdapter

class CategoriesRemoteRequester : BaseRemoteRequester<CategoriesRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/categories.php"

    override val jsonAdapter: JsonAdapter<CategoriesRemote>
        get() = moshi.adapter(CategoriesRemote::class.java)
}