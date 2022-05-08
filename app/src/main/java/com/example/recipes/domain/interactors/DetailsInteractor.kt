package com.example.recipes.domain.interactors

import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.repositories.RecipeRepository

class DetailsInteractor(
    private val recipeRepository: RecipeRepository,
    var endpoint: String = ""
) : Interactor<DetailDomain> {

    override fun execute() = recipeRepository.getDetailDomainList(endpoint)

}