package com.example.recipes.data.datasources.cloud.requesters

import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.squareup.moshi.JsonAdapter

class CategoriesCloudRequester : BaseCloudRequester<CategoriesCloud>() {

    override val BASE_URL = "https://www.themealdb.com/api/json/v1/1/categories.php"

    override val jsonAdapter: JsonAdapter<CategoriesCloud>
        get() = moshi.adapter(CategoriesCloud::class.java)
}