package com.example.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repository.RecipeRepository
import java.lang.IllegalArgumentException

class FeedViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            return FeedViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}