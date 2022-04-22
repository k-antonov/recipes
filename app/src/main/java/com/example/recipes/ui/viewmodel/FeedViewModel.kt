package com.example.recipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.data.model.Recipe
import com.example.recipes.data.repository.RecipeRepository

class FeedViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val mutableRecipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>>
        get() = mutableRecipes

    private val recipesFromRepository: LiveData<Result<List<Recipe>>>
        get() = recipeRepository.recipes

    private val observer = Observer<Result<List<Recipe>>> {
        // как обработать it.isFailure?
        mutableRecipes.value = it.getOrNull()
    }

    init {
        recipesFromRepository.observeForever(observer)
    }

    override fun onCleared() {
        recipesFromRepository.removeObserver(observer)
        super.onCleared()
    }
}