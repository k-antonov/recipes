package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.categories.CategoriesCloudToCategoryDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToDetails
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToFeed
import com.example.recipes.data.datasources.cloud.mappers.cuisines.CuisinesCloudToCuisineDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.previews.PreviewsCloudToPreviewDomainListMapper
import com.example.recipes.domain.entities.*
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    companion object {
        val TAG = RecipeRepositoryImpl::class.java.simpleName
    }

    private val mapperToFeed = RecipeCloudMapperToFeed()
    private val mapperToDetails = RecipeCloudMapperToDetails()

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
        val mapper = CategoriesCloudToCategoryDomainListMapper()
        return mapper.mapLiveData(recipeApiService.getCategoriesCloud())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        val mapper = CuisinesCloudToCuisineDomainListMapper()
        return mapper.mapLiveData(recipeApiService.getCuisinesCloud())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val mapper = PreviewsCloudToPreviewDomainListMapper()
        return mapper.mapLiveData(recipeApiService.getPreviewsCloud(endpoint))
    }
}