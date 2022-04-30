package com.example.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repository.RecipeRepository
import java.lang.IllegalArgumentException

class RecipeDetailsViewModelFactory(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)) {
            return RecipeDetailsViewModel(repository, recipeId) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")

    }
}