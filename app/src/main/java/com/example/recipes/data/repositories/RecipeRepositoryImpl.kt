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

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        val mapper = DetailsCloudToDetailDomainListMapper()
        return mapper.mapLiveData(recipeApiService.getDetailsCloud(endpoint))
    }
}