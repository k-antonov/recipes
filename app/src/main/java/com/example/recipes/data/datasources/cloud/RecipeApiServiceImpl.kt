package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.requesters.CategoriesCloudRequester
import com.example.recipes.data.datasources.cloud.requesters.RecipesCloudRequester
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud
import com.example.recipes.data.datasources.cloud.requesters.CuisinesCloudRequester

class RecipeApiServiceImpl : RecipeApiService {
    private val recipesCloudRequester = RecipesCloudRequester()

    private val categoriesCloudRequester = CategoriesCloudRequester()
    private val cuisinesCloudRequester = CuisinesCloudRequester()

    override fun getRecipeCloudList(): LiveData<Result<List<RecipeCloud>>> {
        return recipesCloudRequester.execute(client)
    }

    override fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>> {
        return categoriesCloudRequester.execute(client)
    }

    override fun getCuisinesCloud(): LiveData<Result<CuisinesCloud>> {
        return cuisinesCloudRequester.execute(client)
    }
}