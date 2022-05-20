package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.recipes.data.datasources.cloud.RecipeApiService
import com.example.recipes.data.datasources.cloud.mappers.CategoriesCloudToCategoryDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.CuisinesCloudToCuisineDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.DetailsCloudToDetailDomainListMapper
import com.example.recipes.data.datasources.cloud.mappers.PreviewsCloudToPreviewDomainListMapper
import com.example.recipes.data.datasources.local.DatabaseSource
import com.example.recipes.data.datasources.local.repositoryqueries.DetailInserter
import com.example.recipes.data.datasources.local.repositoryqueries.DetailSelector
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.RecipeRepository
import com.example.recipes.utils.observeOnce

class RecipeRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val databaseSource: DatabaseSource
) : RecipeRepository {

    private val categoryMapper: CategoriesCloudToCategoryDomainListMapper by lazy { CategoriesCloudToCategoryDomainListMapper() }
    private val cuisineMapper: CuisinesCloudToCuisineDomainListMapper by lazy { CuisinesCloudToCuisineDomainListMapper() }
    private val previewMapper: PreviewsCloudToPreviewDomainListMapper by lazy { PreviewsCloudToPreviewDomainListMapper() }
    private val detailMapper: DetailsCloudToDetailDomainListMapper by lazy { DetailsCloudToDetailDomainListMapper() }

    private val detailInserter: DetailInserter by lazy { DetailInserter(databaseSource) }
    private val detailSelector: DetailSelector by lazy { DetailSelector(databaseSource) }

    override fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>> {
        return categoryMapper.mapLiveData(recipeApiService.getCategoriesCloud())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        return cuisineMapper.mapLiveData(recipeApiService.getCuisinesCloud())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        return previewMapper.mapLiveData(recipeApiService.getPreviewsCloud(endpoint))
    }

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        val mappedToDomain = detailMapper.mapLiveData(recipeApiService.getDetailsCloud(endpoint))

        val observer = Observer<Result<List<DetailDomain>>> { result ->
            result.onSuccess {
                val value = it[0]
                detailInserter.insertIfNotExists(value)
            }
        }

        mappedToDomain.observeOnce(observer)

        return mappedToDomain
    }

    override fun getLocalPreviewDomainList(): LiveData<Result<List<PreviewDomain>>> {
        val liveData = MutableLiveData(databaseSource.getRecipeDao().getPreviews())

        val liveDataResult = Transformations.map(liveData) {
            Result.success(it)
        }

        return liveDataResult
    }

    override fun getLocalDetailDomainList(id: Long): LiveData<Result<List<DetailDomain>>> {
        val detailDomain = detailSelector.select(id)
        return MutableLiveData(Result.success(listOf(detailDomain)))
    }

    override fun clearCache() {
        databaseSource.clearAllTables()
    }
}