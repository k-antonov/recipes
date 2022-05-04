package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeFeedInteractor(private val recipeRepository: RecipeRepository) {

    fun execute(): LiveData<Result<List<RecipeFeedDomain>>> = recipeRepository.recipeFeedDomain

}