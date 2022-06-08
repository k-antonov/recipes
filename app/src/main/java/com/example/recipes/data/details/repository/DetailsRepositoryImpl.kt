package com.example.recipes.data.details.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipes.data.categories.datasources.local.CategoryLocalDataSource
import com.example.recipes.data.core.datasources.remote.RecipeApiService
import com.example.recipes.data.core.repository.observeOnce
import com.example.recipes.data.cuisines.datasources.local.CuisineLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.IngredientLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.MeasureLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.RecipeLocalDataSource
import com.example.recipes.data.details.datasources.local.localdatasources.RecipesToIngredientsAndMeasuresLocalDataSource
import com.example.recipes.data.details.datasources.remote.DetailsRemote
import com.example.recipes.data.details.datasources.remote.DetailsRemoteToDetailDomainListMapper
import com.example.recipes.domain.details.DetailDomain
import com.example.recipes.domain.details.DetailsRepository

class DetailsRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val cuisineLocalDataSource: CuisineLocalDataSource,
    private val recipeLocalDataSource: RecipeLocalDataSource,
    private val ingredientLocalDataSource: IngredientLocalDataSource,
    private val measureLocalDataSource: MeasureLocalDataSource,
    private val recipesToIngredientsAndMeasuresLocalDataSource: RecipesToIngredientsAndMeasuresLocalDataSource
) : DetailsRepository {

    private val mapper: DetailsRemoteToDetailDomainListMapper by lazy { DetailsRemoteToDetailDomainListMapper() }

    private val observer = Observer<Result<List<DetailDomain>>> { result ->
        result.onSuccess {
            val detailDomain = it[0]
            val categoryId = categoryLocalDataSource.getIdByName(detailDomain.nameCategory)
            val cuisineId = cuisineLocalDataSource.getIdByName(detailDomain.nameCuisine)
            recipeLocalDataSource.insertDetail(detailDomain, categoryId, cuisineId)

            for (pair in detailDomain.ingredients.zip(detailDomain.measures)) {
                val ingredientId = ingredientLocalDataSource.insert(pair.first)
                val measureId = measureLocalDataSource.insert(pair.second)
                recipesToIngredientsAndMeasuresLocalDataSource.insert(
                    detailDomain.id,
                    ingredientId,
                    measureId
                )
            }
        }
    }

    override fun fetchData(recipeId: Long): LiveData<Result<List<DetailDomain>>> {
        val localData = fetchLocalData(recipeId)

        return if (localData == null) {
            val remoteData = fetchRemoteData(recipeId)
            val mappedToDomain = mapper.mapLiveData(remoteData)
            mappedToDomain.observeOnce(observer)
            mappedToDomain
        } else {
            // todo избавиться от списка
            MutableLiveData(Result.success(listOf(localData)))
        }
    }

    private fun fetchRemoteData(recipeId: Long): LiveData<Result<DetailsRemote>> {
        return recipeApiService.getDetailsRemote(recipeId)
    }

    private fun fetchLocalData(id: Long): DetailDomain? {
        return recipeLocalDataSource.loadDetail(id)
    }

    override fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        recipeLocalDataSource.changeFavoriteStatus(recipeId, isFavorite)
    }
}