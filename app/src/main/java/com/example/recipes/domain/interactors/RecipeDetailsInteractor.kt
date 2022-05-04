package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.repositories.RecipeRepository
import com.example.recipes.domain.entities.RecipeDetailsDomain

class RecipeDetailsInteractor(private val recipeRepository: RecipeRepository) {

    fun execute(recipeId: Int): LiveData<Result<RecipeDetailsDomain>> {
        return recipeRepository.getRecipeDetailsDomainById(recipeId)
    }

}