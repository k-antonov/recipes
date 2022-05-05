package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.RecipeRepository

class CategoriesInteractor(private val recipeRepository: RecipeRepository) {

    fun execute() = recipeRepository.getCategoryDomainList()

}