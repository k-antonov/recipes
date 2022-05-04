package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.commands.RecipesCloudRequester
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud

class RecipeApiServiceImpl : RecipeApiService {
    private val recipesCloudRequester = RecipesCloudRequester()

    override fun getRecipeCloudList(itemsNum: Int): LiveData<Result<List<RecipeCloud>>> {
        return recipesCloudRequester.execute(client, itemsNum)
    }
}