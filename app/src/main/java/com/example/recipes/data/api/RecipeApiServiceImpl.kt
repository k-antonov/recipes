package com.example.recipes.data.api

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.model.Recipe

class RecipeApiServiceImpl : RecipeApiService {
    private val getRequestCommand = GetRequest()

    override fun getRecipeList(): LiveData<Result<List<Recipe>>> {
        return getRequestCommand.execute(client)
    }
}