package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToDetails
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToFeed
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    companion object {
        val TAG = RecipeRepositoryImpl::class.java.simpleName

        const val FEED_ITEMS_NUM = 10
        const val TOTAL_ITEMS_NUM = 2199
    }

    private val mapperToFeed = RecipeCloudMapperToFeed()
    private val mapperToDetails = RecipeCloudMapperToDetails()

    override val recipeFeedDomain: LiveData<Result<List<RecipeFeedDomain>>>
        get() {
            val recipeCloudList = recipeApiService.getRecipeCloudList(FEED_ITEMS_NUM)
            return mapperToFeed.mapToLiveData(recipeCloudList)
        }

    override fun getRecipeDetailsDomainById(recipeId: Int): LiveData<Result<RecipeDetailsDomain>> {
        val recipeCloudList = recipeApiService.getRecipeCloudList(TOTAL_ITEMS_NUM)

        // или нет
        val recipeCloudListMappedToFeed = mapperToFeed.mapToLiveData(recipeCloudList)

        return mapperToDetails.mapToLiveData(recipeCloudList, recipeId)
    }

}