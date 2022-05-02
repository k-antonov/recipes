package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain

interface RecipeRepository {

    val recipeFeedDomainLiveData: LiveData<Result<List<RecipeFeedDomain>>>//List<RecipeFeedDomain>

    val recipeDetailsDomain: RecipeDetailsDomain
}