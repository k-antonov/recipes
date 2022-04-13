package com.example.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repository.RecipeRepository
import java.lang.IllegalArgumentException

// возможно стоит сделать класс более обобщенным, чтобы мог создавать
// ViewModels, зависящие не только от RecipeRepository
class ViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}