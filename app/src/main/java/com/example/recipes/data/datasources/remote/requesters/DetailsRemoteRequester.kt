package com.example.recipes.data.datasources.remote.requesters

import com.example.recipes.data.datasources.remote.entities.DetailsRemote
import com.squareup.moshi.JsonAdapter

class DetailsRemoteRequester : BaseRemoteRequester<DetailsRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="

    override val jsonAdapter: JsonAdapter<DetailsRemote>
        get() = moshi.adapter(DetailsRemote::class.java)
}