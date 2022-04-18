package com.example.recipes.data.repository

import androidx.lifecycle.LiveData
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.model.Recipe

class RecipeRepository(private val recipeApiService: RecipeApiService) {

    val recipes: LiveData<Result<List<Recipe>>>
        get() = recipeApiService.fetch()
}