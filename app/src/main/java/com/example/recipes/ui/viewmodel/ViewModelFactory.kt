package com.example.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repository.RecipeRepository
import java.lang.IllegalArgumentException

// возможно стоит сделать класс более обобщенным, чтобы мог создавать
// ViewModels, зависящие не только от RecipeRepository
class ViewModelFactory(
    private val repository: RecipeRepository,
    private val recipeId: Int? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FeedViewModel::class.java) -> FeedViewModel(repository) as T
            modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java) -> recipeId?.let {
                RecipeDetailsViewModel(repository, it)
            } as T
            else -> throw ClassNotFoundException("Unknown class")
        }
    }
}