package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToFeed
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    companion object {
        val TAG = RecipeRepositoryImpl::class.java.simpleName
    }

    private val mapper = RecipeCloudMapperToFeed()

    override val recipeFeedDomainLiveData: LiveData<Result<List<RecipeFeedDomain>>>
        get() {
            val recipeCloudList = recipeApiService.getRecipeCloudList()

            // проблема из-за создания нового экземпляра лайвдаты
            return mapper.mapToLiveData(recipeCloudList)
        }

    override val recipeDetailsDomain: RecipeDetailsDomain
        get() {
            return RecipeDetailsDomain(
                0, "mockTitle", listOf("mock ingredients"),
                listOf("mock instructions"), "mockUrl"
            )
        }

}