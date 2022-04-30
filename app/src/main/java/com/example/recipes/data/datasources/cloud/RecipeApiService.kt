package com.example.recipes.data.datasources.cloud

import androidx.lifecycle.LiveData
import com.example.recipes.data.entities.RecipeData

interface RecipeApiService {
    fun getRecipeDataList() : LiveData<Result<List<RecipeData>>>
}