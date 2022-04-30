package com.example.recipes.domain.interactors

import com.example.recipes.domain.repositories.RecipeRepository
import com.example.recipes.domain.entities.RecipeFeedDomain

class RecipeFeedInteractor(private val recipeRepository: RecipeRepository) {

    // возвращать LiveData с Result/Resource
    fun execute(): List<RecipeFeedDomain> = recipeRepository.recipeFeedDomainList

}