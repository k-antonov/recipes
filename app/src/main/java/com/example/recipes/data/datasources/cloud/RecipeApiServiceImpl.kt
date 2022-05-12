package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.entities.*
import com.example.recipes.data.datasources.cloud.requesters.*

class RecipeApiServiceImpl : RecipeApiService {

    // как тут избавиться от повторяющегося кода?
    override fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>> {
        val requester = CategoriesCloudRequester()
        return requester.execute(client)
    }

    override fun getCuisinesCloud(): LiveData<Result<CuisinesCloud>> {
        val requester = CuisinesCloudRequester()
        return requester.execute(client)
    }

    override fun getPreviewsCloud(endpoint: String): LiveData<Result<PreviewsCloud>> {
        val requester = PreviewsCloudRequester()
        return requester.execute(client, endpoint)
    }

    override fun getDetailsCloud(endpoint: String): LiveData<Result<DetailsCloud>> {
        val requester = DetailsCloudRequester()
        return requester.execute(client, endpoint)
    }

}