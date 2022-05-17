package com.example.recipes.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.CategoriesCloudToCategoryDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.CuisinesCloudToCuisineDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.DetailsCloudToDetailDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.PreviewsCloudToPreviewDomainListMapper
import com.example.recipes.data.datasources.local.CategoryDb
import com.example.recipes.data.datasources.local.CuisineDb
import com.example.recipes.data.datasources.local.LocalDataSource
import com.example.recipes.data.datasources.local.RecipeDb
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository

class RecipeRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val localDataSource: LocalDataSource
) : RecipeRepository {

    private val categoryMapper: CategoriesCloudToCategoryDomainListMapper by lazy { CategoriesCloudToCategoryDomainListMapper() }
    private val cuisineMapper: CuisinesCloudToCuisineDomainListMapper by lazy { CuisinesCloudToCuisineDomainListMapper() }
    private val previewMapper: PreviewsCloudToPreviewDomainListMapper by lazy { PreviewsCloudToPreviewDomainListMapper() }
    private val detailMapper: DetailsCloudToDetailDomainListMapper by lazy { DetailsCloudToDetailDomainListMapper() }

    override fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>> {
        return categoryMapper.mapLiveData(recipeApiService.getCategoriesCloud())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        return cuisineMapper.mapLiveData(recipeApiService.getCuisinesCloud())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val previewsCloud = recipeApiService.getPreviewsCloud(endpoint)



        return previewMapper.mapLiveData(previewsCloud)
    }

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        val detailDomain = detailMapper.mapLiveData(recipeApiService.getDetailsCloud(endpoint))

        detailDomain.observeForever { result ->
            result.onSuccess {
                Log.d("Repository", "inside onSuccess")
                val value = it[0]

                var categoryId = localDataSource.getCategoryDao().getIdByName(value.nameCategory)
                if (categoryId == 0L) {
                    categoryId = localDataSource.getCategoryDao().insert(
                        CategoryDb(
                            name = value.nameCategory
                        )
                    )
                }

                var cuisineId = localDataSource.getCuisineDao().getIdByName(value.nameCuisine)
                if (cuisineId == 0L) {
                    cuisineId = localDataSource.getCuisineDao().insert(
                        CuisineDb(
                            name = value.nameCuisine
                        )
                    )
                }

                localDataSource.getRecipeDao().insert(
                    RecipeDb(
                        id = value.id.toLong(),
                        name = value.name,
                        categoryId = categoryId,
                        cuisineId = cuisineId,
                        instructions = value.strInstructions,
                        imageUrl = value.imageUrl
                    )
                )
            }
        }

        return detailDomain
    }

    override fun getLocalPreviewDomainList(): LiveData<Result<List<PreviewDomain>>> {
        val liveData = localDataSource.getRecipeDao().getPreviews()

        val liveDataResult = Transformations.map(liveData) {
            Result.success(it)
        }
        return liveDataResult
    }

    override fun clearCache() {
        localDataSource.clearAllTables()
    }
}