package com.example.recipes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.api.RecipeApiService
import com.example.recipes.data.model.Recipe
import com.example.recipes.utils.Resource

class RecipeRepository(private val recipeApiService: RecipeApiService) {

    private val mutableRecipes = MutableLiveData<Resource<List<Recipe>>>()

    val recipes: LiveData<Resource<List<Recipe>>>
        get() {
            mutableRecipes.value = recipeApiService.fetch() // todo мб postValue?
            return mutableRecipes
        }
}