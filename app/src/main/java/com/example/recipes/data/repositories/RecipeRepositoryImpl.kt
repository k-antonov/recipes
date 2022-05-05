package com.example.recipes.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToDetails
import com.example.recipes.data.datasources.cloud.mappers.RecipeCloudMapperToFeed
import com.example.recipes.data.datasources.cloud.mappers.categories.CategoriesCloudToCategoryCloudMapper
import com.example.recipes.data.datasources.cloud.mappers.categories.CategoryCloudToCategoryDomainMapper
import com.example.recipes.data.datasources.cloud.mappers.cuisines.CuisineCloudToCuisineDomainMapper
import com.example.recipes.data.datasources.cloud.mappers.cuisines.CuisinesCloudToCuisinesCloudMapper
import com.example.recipes.data.datasources.cloud.mappers.previews.PreviewCloudToPreviewDomainMapper
import com.example.recipes.data.datasources.cloud.mappers.previews.PreviewsCloudToPreviewCloudMapper
import com.example.recipes.domain.entities.*
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    companion object {
        val TAG = RecipeRepositoryImpl::class.java.simpleName
    }

    private val mapperToFeed = RecipeCloudMapperToFeed()
    private val mapperToDetails = RecipeCloudMapperToDetails()

    private val toCategoryCloudMapper = CategoriesCloudToCategoryCloudMapper()
    private val toCategoryDomainMapper = CategoryCloudToCategoryDomainMapper()

    private val toCuisineCloudMapper = CuisinesCloudToCuisinesCloudMapper()
    private val toCuisineDomainMapper = CuisineCloudToCuisineDomainMapper()

    private val toPreviewCloudMapper = PreviewsCloudToPreviewCloudMapper()
    private val toPreviewDomainMapper = PreviewCloudToPreviewDomainMapper()

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

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        val cuisinesCloud = recipeApiService.getCuisinesCloud()
        val cuisineCloudList = toCuisineCloudMapper.mapToLiveData(cuisinesCloud)
        return toCuisineDomainMapper.mapToLiveData(cuisineCloudList)
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val previewsCloud = recipeApiService.getPreviewsCloud(endpoint)
        val previewCloudList = toPreviewCloudMapper.mapToLiveData(previewsCloud)
        return toPreviewDomainMapper.mapToLiveData(previewCloudList)
    }
}