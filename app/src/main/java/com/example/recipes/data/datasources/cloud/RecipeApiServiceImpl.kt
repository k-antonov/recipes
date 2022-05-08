package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.entities.*
import com.example.recipes.data.datasources.cloud.requesters.*

class RecipeApiServiceImpl : RecipeApiService {
    private val recipesCloudRequester = RecipesCloudRequester()

    private val categoriesCloudRequester = CategoriesCloudRequester()
    private val cuisinesCloudRequester = CuisinesCloudRequester()
    private val previewsCloudRequester = PreviewsCloudRequester()
    private val detailsCloudRequester = DetailsCloudRequester()

    override fun getRecipeCloudList(): LiveData<Result<List<RecipeCloud>>> {
        return recipesCloudRequester.execute(client)
    }

    override fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>> {
        return categoriesCloudRequester.execute(client)
    }

    override fun getCuisinesCloud(): LiveData<Result<CuisinesCloud>> {
        return cuisinesCloudRequester.execute(client)
    }

    override fun getPreviewsCloud(endpoint: String): LiveData<Result<PreviewsCloud>> {
        return previewsCloudRequester.execute(client, endpoint)
    }

    override fun getDetailsCloud(endpoint: String): LiveData<Result<DetailsCloud>> {
        return detailsCloudRequester.execute(client, endpoint)
    }


}