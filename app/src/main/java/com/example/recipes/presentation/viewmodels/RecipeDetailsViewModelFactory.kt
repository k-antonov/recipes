package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repositories.RecipeRepositoryImpl
import com.example.recipes.domain.interactors.RecipeDetailsInteractor
import java.lang.IllegalArgumentException

class RecipeDetailsViewModelFactory(
    private val recipeDetailsInteractor: RecipeDetailsInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)) {
            return RecipeDetailsViewModel(recipeDetailsInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")

    }
}