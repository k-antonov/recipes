package com.example.recipes.domain.interactors

import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.repositories.RecipeRepository

class CuisinesInteractor(private val recipeRepository: RecipeRepository) : Interactor<CuisineDomain> {

    override fun execute() = recipeRepository.getCuisineDomainList()

}