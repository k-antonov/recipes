package com.example.recipes.data.datasources.remote.requesters

import com.example.recipes.data.datasources.remote.entities.PreviewsRemote
import com.squareup.moshi.JsonAdapter

class PreviewsRemoteRequester : BaseRemoteRequester<PreviewsRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?"

    override val jsonAdapter: JsonAdapter<PreviewsRemote>
        get() = moshi.adapter(PreviewsRemote::class.java)
}