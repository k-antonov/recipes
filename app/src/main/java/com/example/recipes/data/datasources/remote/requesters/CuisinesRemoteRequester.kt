package com.example.recipes.data.datasources.remote.requesters

import com.example.recipes.data.datasources.remote.entities.CuisinesRemote
import com.squareup.moshi.JsonAdapter

class CuisinesRemoteRequester : BaseRemoteRequester<CuisinesRemote>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/list.php?a=list"

    override val jsonAdapter: JsonAdapter<CuisinesRemote>
        get() = moshi.adapter(CuisinesRemote::class.java)
}