package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.RecipeRepository
import com.example.recipes.domain.entities.RecipeDetailsDomain

class RecipeDetailsInteractor(private val recipeRepository: RecipeRepository) {

    fun execute(): RecipeDetailsDomain = recipeRepository.recipeDetailsDomain

}