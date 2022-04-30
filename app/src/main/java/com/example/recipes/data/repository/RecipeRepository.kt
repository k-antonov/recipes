package com.example.recipes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.model.Recipe

class RecipeRepository(private val recipeApiService: RecipeApiService) {

    val recipes: LiveData<Result<List<Recipe>>>
        get() {
            Log.d("RecipeRepository", "calling getRecipeList $this")
            return recipeApiService.getRecipeList()
        }
}