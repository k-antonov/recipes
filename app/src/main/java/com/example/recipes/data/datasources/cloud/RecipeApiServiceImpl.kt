package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.MyApplication.Companion.client
import com.example.recipes.data.datasources.cloud.commands.GetRequest
import com.example.recipes.data.entities.RecipeData

class RecipeApiServiceImpl : RecipeApiService {
    private val getRequestCommand = GetRequest()

    override fun getRecipeDataList(): LiveData<Result<List<RecipeData>>> {
        return getRequestCommand.execute(client)
    }
}