package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.CategoriesCloudToCategoryDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.CuisinesCloudToCuisineDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.DetailsCloudToDetailDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.PreviewsCloudToPreviewDomainListMapper
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(private val recipeApiService: RecipeApiService) : RecipeRepository {

    private val categoryMapper: CategoriesCloudToCategoryDomainListMapper by lazy { CategoriesCloudToCategoryDomainListMapper() }
    private val cuisineMapper: CuisinesCloudToCuisineDomainListMapper by lazy { CuisinesCloudToCuisineDomainListMapper() }
    private val previewMapper: PreviewsCloudToPreviewDomainListMapper by lazy { PreviewsCloudToPreviewDomainListMapper() }
    private val detailMapper: DetailsCloudToDetailDomainListMapper by lazy { DetailsCloudToDetailDomainListMapper() }

    override fun getCategoryDomainList() : LiveData<Result<List<CategoryDomain>>> {
        return categoryMapper.mapLiveData(recipeApiService.getCategoriesCloud())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        return cuisineMapper.mapLiveData(recipeApiService.getCuisinesCloud())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        return previewMapper.mapLiveData(recipeApiService.getPreviewsCloud(endpoint))
    }

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        return detailMapper.mapLiveData(recipeApiService.getDetailsCloud(endpoint))
    }
}