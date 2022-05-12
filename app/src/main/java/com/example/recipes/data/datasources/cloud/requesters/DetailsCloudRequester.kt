package com.example.recipes.data.datasources.cloud.requesters

import com.example.recipes.data.datasources.cloud.entities.DetailsCloud
import com.squareup.moshi.JsonAdapter

class DetailsCloudRequester : BaseCloudRequester<DetailsCloud>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="

    override val jsonAdapter: JsonAdapter<DetailsCloud>
        get() = moshi.adapter(DetailsCloud::class.java)
}