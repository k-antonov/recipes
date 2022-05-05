package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.requesters.CategoriesCloudRequester
import com.example.recipes.data.datasources.cloud.requesters.RecipesCloudRequester
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud
import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.example.recipes.data.datasources.cloud.requesters.CuisinesCloudRequester
import com.example.recipes.data.datasources.cloud.requesters.PreviewsCloudRequester

class RecipeApiServiceImpl : RecipeApiService {
    private val recipesCloudRequester = RecipesCloudRequester()

    private val categoriesCloudRequester = CategoriesCloudRequester()
    private val cuisinesCloudRequester = CuisinesCloudRequester()
    private val previewsCloudRequester = PreviewsCloudRequester()

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
}