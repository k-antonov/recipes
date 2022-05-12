package com.example.recipes.data.datasources.cloud.requesters

import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud
import com.squareup.moshi.JsonAdapter

class PreviewsCloudRequester : BaseCloudRequester<PreviewsCloud>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/filter.php?"

    override val jsonAdapter: JsonAdapter<PreviewsCloud>
        get() = moshi.adapter(PreviewsCloud::class.java)
}