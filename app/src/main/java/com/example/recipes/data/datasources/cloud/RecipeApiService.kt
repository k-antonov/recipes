package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.entities.RecipeCloud

interface RecipeApiService {
    fun getRecipeCloudList(itemsNum: Int) : LiveData<Result<List<RecipeCloud>>>
}