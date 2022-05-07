package com.example.recipes.domain.interactors

import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository

class PreviewsInteractor(
    private val recipeRepository: RecipeRepository,
    var endpoint: String = ""
) : Interactor<PreviewDomain> {

    override fun execute() = recipeRepository.getPreviewDomainList(endpoint)

}