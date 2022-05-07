package com.example.recipes.domain.interactors

import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.repositories.RecipeRepository

class CategoriesInteractor(private val recipeRepository: RecipeRepository) : Interactor<CategoryDomain> {

    override fun execute() = recipeRepository.getCategoryDomainList()

}