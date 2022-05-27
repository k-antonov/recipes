package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.recipes.MyApplication.Companion.categoryLocalDataSource
import com.example.recipes.MyApplication.Companion.cuisineLocalDataSource
import com.example.recipes.MyApplication.Companion.ingredientLocalDataSource
import com.example.recipes.MyApplication.Companion.measureLocalDataSource
import com.example.recipes.MyApplication.Companion.recipeLocalDataSource
import com.example.recipes.MyApplication.Companion.recipesToIngredientsAndMeasuresLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.DetailsRemote
import com.example.recipes.data.datasources.remote.mappers.DetailsRemoteToDetailDomainListMapper
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.repositories.DetailsRepository
import com.example.recipes.utils.observeOnce

class DetailsRepositoryImpl(private val apiService: RecipeApiService) : DetailsRepository {

    private val mapper: DetailsRemoteToDetailDomainListMapper by lazy { DetailsRemoteToDetailDomainListMapper() }

    private val observer = Observer<Result<List<DetailDomain>>> { result ->
        result.onSuccess {
            val detailDomain = it[0]
            val categoryId =
                categoryLocalDataSource.insert(detailDomain.nameCategory, detailDomain.imageUrl)
            val cuisineId =
                cuisineLocalDataSource.insert(detailDomain.nameCuisine, detailDomain.imageUrl)
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
        return apiService.getDetailsRemote(recipeId)
    }

    private fun fetchLocalData(id: Long): DetailDomain? {
        return recipeLocalDataSource.loadDetail(id)
    }

    override fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        recipeLocalDataSource.changeFavoriteStatus(recipeId, isFavorite)
    }
}