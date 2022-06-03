package com.example.recipes.data.details.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseRemoteRequester
import com.squareup.moshi.JsonAdapter

class DetailsRemoteRequester : BaseRemoteRequester<DetailsRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="

    override val jsonAdapter: JsonAdapter<DetailsRemote>
        get() = moshi.adapter(DetailsRemote::class.java)
}