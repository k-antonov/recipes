package com.example.recipes.data.datasources.cloud.requesters

import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud
import com.squareup.moshi.JsonAdapter

class CuisinesCloudRequester : BaseCloudRequester<CuisinesCloud>() {
    override val BASE_URL: String
        get() = "https://www.themealdb.com/api/json/v1/1/list.php?a=list"
    override val jsonAdapter: JsonAdapter<CuisinesCloud>
        get() = moshi.adapter(CuisinesCloud::class.java)
}