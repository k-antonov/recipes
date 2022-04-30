package com.example.recipes.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recipes.data.model.Recipe
import com.example.recipes.data.repository.RecipeRepository

// todo нужно ли вообще передавать RecipeRepository или можно воспользоваться
// top-level recipeRepository из MainActivity?
class RecipeDetailsViewModel(
    private val repository: RecipeRepository,
    private val recipeId: Int
) : ViewModel() {

    // todo переписать с использованием LiveData
    val recipe: Recipe
        get() {
            val result = repository.recipes.value
            val list = result?.getOrNull()

            if (list != null) {
                return list.first { it.id == recipeId }
            } else {
                // todo переделать в AlertDialog, например
                throw Exception("Result failed")
            }
        }
}