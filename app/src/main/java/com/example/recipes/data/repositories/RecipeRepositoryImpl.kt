package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.mappers.CategoriesRemoteToCategoryDomainListMapper
import com.example.recipes.data.datasources.remote.mappers.CuisinesRemoteToCuisineDomainListMapper
import com.example.recipes.data.datasources.remote.mappers.DetailsRemoteToDetailDomainListMapper
import com.example.recipes.data.datasources.remote.mappers.PreviewsRemoteToPreviewDomainListMapper
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

    private val categoryMapper: CategoriesRemoteToCategoryDomainListMapper by lazy { CategoriesRemoteToCategoryDomainListMapper() }
    private val cuisineMapper: CuisinesRemoteToCuisineDomainListMapper by lazy { CuisinesRemoteToCuisineDomainListMapper() }
    private val previewMapper: PreviewsRemoteToPreviewDomainListMapper by lazy { PreviewsRemoteToPreviewDomainListMapper() }
    private val detailMapper: DetailsRemoteToDetailDomainListMapper by lazy { DetailsRemoteToDetailDomainListMapper() }

    private val detailInserter: DetailInserter by lazy { DetailInserter(databaseSource) }
    private val detailSelector: DetailSelector by lazy { DetailSelector(databaseSource) }

    override fun getCategoryDomainList(): LiveData<Result<List<CategoryDomain>>> {
        return categoryMapper.mapLiveData(recipeApiService.getCategoriesRemote())
    }

    override fun getCuisineDomainList(): LiveData<Result<List<CuisineDomain>>> {
        return cuisineMapper.mapLiveData(recipeApiService.getCuisinesRemote())
    }

    override fun getPreviewDomainList(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        return previewMapper.mapLiveData(recipeApiService.getPreviewsRemote(endpoint))
    }

    override fun getDetailDomainList(endpoint: String): LiveData<Result<List<DetailDomain>>> {
        val mappedToDomain = detailMapper.mapLiveData(recipeApiService.getDetailsRemote(endpoint))

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