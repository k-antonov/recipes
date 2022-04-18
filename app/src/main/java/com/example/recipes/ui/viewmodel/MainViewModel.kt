package com.example.recipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.recipes.data.model.Recipe
import com.example.recipes.data.repository.RecipeRepository

class MainViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    val recipes: LiveData<Result<List<Recipe>>>
        get() = recipeRepository.recipes
}