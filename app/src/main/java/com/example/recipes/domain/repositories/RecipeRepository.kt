package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain

interface RecipeRepository {

    val recipeFeedDomain: LiveData<Result<List<RecipeFeedDomain>>>

    fun getRecipeDetailsDomainById(recipeId: Int): LiveData<Result<RecipeDetailsDomain>>

//    val recipeDetailsDomain: LiveData<Result<List<RecipeDetailsDomain>>>
}