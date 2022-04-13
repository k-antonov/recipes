package com.example.recipes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipes.data.model.Recipe
import com.example.recipes.data.repository.RecipeRepository
import com.example.recipes.utils.Resource

class MainViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    private val mutableRecipes = MutableLiveData<Resource<List<Recipe>>>()

    val recipes: LiveData<Resource<List<Recipe>>>
        get() = mutableRecipes

    init {
        // может быть postValue?
        mutableRecipes.value = Resource.loading(null)
        // передаём данные от репозитория к ViewModel
        mutableRecipes.value = recipeRepository.recipes.value
    }
}