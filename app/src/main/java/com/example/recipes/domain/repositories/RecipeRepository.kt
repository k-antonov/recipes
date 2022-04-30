package com.example.recipes.domain.repositories

import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain

interface RecipeRepository {

    val recipeFeedDomainList: List<RecipeFeedDomain>

    val recipeDetailsDomain: RecipeDetailsDomain
}