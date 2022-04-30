package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.data.repositories.RecipeRepositoryImpl
import com.example.recipes.domain.interactors.RecipeFeedInteractor
import java.lang.IllegalArgumentException

class RecipeFeedViewModelFactory(
    private val recipeFeedInteractor: RecipeFeedInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeFeedViewModel::class.java)) {
            return RecipeFeedViewModel(recipeFeedInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}