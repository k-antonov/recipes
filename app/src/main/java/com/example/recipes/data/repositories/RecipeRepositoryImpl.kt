package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.categories.CategoriesCloudToCategoryCloudMapper
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToDetails
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToFeed
import com.example.recipes.data.datasources.cloud.mappers.categories.CategoryCloudToCategoryDomainMapper
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    companion object {
        val TAG = RecipeRepositoryImpl::class.java.simpleName
    }

    private val mapperToFeed = RecipeCloudMapperToFeed()
    private val mapperToDetails = RecipeCloudMapperToDetails()

    private val toCategoryCloudMapper = CategoriesCloudToCategoryCloudMapper()
    private val toCategoryDomainMapper = CategoryCloudToCategoryDomainMapper()

    override val recipeFeedDomain: LiveData<Result<List<RecipeFeedDomain>>>
        get() {
            val recipeCloudList = recipeApiService.getRecipeCloudList()
            return mapperToFeed.mapToLiveData(recipeCloudList)
        }

    override fun getRecipeDetailsDomainById(recipeId: Int): LiveData<Result<RecipeDetailsDomain>> {
        val recipeCloudList = recipeApiService.getRecipeCloudList()

        // или нет
        val recipeCloudListMappedToFeed = mapperToFeed.mapToLiveData(recipeCloudList)

        return mapperToDetails.mapToLiveData(recipeCloudList, recipeId)
    }

    override fun getCategoryDomainList() : LiveData<Result<List<CategoryDomain>>> {
        val categoriesCloud = recipeApiService.getCategoriesCloud()
        val categoryCloudList = toCategoryCloudMapper.mapToLiveData(categoriesCloud)
        return toCategoryDomainMapper.mapToLiveData(categoryCloudList)
    }
}