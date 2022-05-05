package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.RecipeRepository

class PreviewsInteractor(private val recipeRepository: RecipeRepository) {

    fun execute(endpoint: String) = recipeRepository.getPreviewDomainList(endpoint)

}