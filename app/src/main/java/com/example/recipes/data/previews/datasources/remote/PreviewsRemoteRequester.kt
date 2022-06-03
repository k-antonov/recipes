package com.example.recipes.data.previews.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseRemoteRequester
import com.squareup.moshi.JsonAdapter

class PreviewsRemoteRequester : BaseRemoteRequester<PreviewsRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?"
//    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?c="

    override val jsonAdapter: JsonAdapter<PreviewsRemote>
        get() = moshi.adapter(PreviewsRemote::class.java)
}