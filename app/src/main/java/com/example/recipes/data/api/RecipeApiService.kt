package com.example.recipes.data.api

import androidx.lifecycle.LiveData
import com.example.recipes.data.model.Recipe

interface RecipeApiService {
    fun getRecipeList() : LiveData<Result<List<Recipe>>>
}