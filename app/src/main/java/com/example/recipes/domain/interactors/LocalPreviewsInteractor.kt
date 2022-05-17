package com.example.recipes.domain.interactors

import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository

class LocalPreviewsInteractor(private val recipeRepository: RecipeRepository) : Interactor<PreviewDomain> {

    override fun execute() = recipeRepository.getLocalPreviewDomainList()
}