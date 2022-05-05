package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.commands.CategoriesCloudRequester
import com.example.recipes.data.datasources.cloud.commands.RecipesCloudRequester
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud

class RecipeApiServiceImpl : RecipeApiService {
    private val recipesCloudRequester = RecipesCloudRequester()
    private val categoriesCloudRequester = CategoriesCloudRequester()

    override fun getRecipeCloudList(): LiveData<Result<List<RecipeCloud>>> {
        return recipesCloudRequester.execute(client)
    }

    override fun getCategoriesCloud(): LiveData<Result<CategoriesCloud>> {
        return categoriesCloudRequester.execute(client)
    }
}