package com.example.recipes.data.categories.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.categories.datasources.local.CategoryLocalDataSource
import com.example.recipes.data.core.datasources.remote.RecipeApiService
import com.example.recipes.data.categories.datasources.remote.CategoriesRemote
import com.example.recipes.data.categories.datasources.remote.CategoriesRemoteToCategoryDomainListMapper
import com.example.recipes.data.core.repository.FetchDataCallback
import com.example.recipes.domain.categories.CategoryDomain
import com.example.recipes.domain.categories.CategoriesRepository
import com.example.recipes.data.core.repository.observeOnce

class CategoriesRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : CategoriesRepository {

    private val mapper: CategoriesRemoteToCategoryDomainListMapper by lazy { CategoriesRemoteToCategoryDomainListMapper() }

    private val liveDataToReturn = MutableLiveData<Result<List<CategoryDomain>>>()

    override fun fetchData(): LiveData<Result<List<CategoryDomain>>> {
        val localDataList = fetchLocalData()
        if (localDataList.isNotEmpty()) {
            liveDataToReturn.value = Result.success(localDataList)
        }

        wrapFetchingRemoteData(object : FetchDataCallback {
            override fun onSuccess() {
                val remoteDataList = fetchRemoteData()
                val mappedToDomain = mapper.mapLiveData(remoteDataList)
                mappedToDomain.observeOnce { result ->
                    result.onSuccess {
                        liveDataToReturn.value = Result.success(it)
                        categoryLocalDataSource.insertList(it)
                    }
                    result.onFailure {
                        if (localDataList.isEmpty()) {
                            liveDataToReturn.value =
                                Result.failure(NetworkErrorException("Network error"))
                        }
                    }
                }
            }
        })

        return liveDataToReturn
    }

    private fun wrapFetchingRemoteData(fetchDataCallback: FetchDataCallback) {
        fetchDataCallback.onSuccess()
    }

    private fun fetchRemoteData(): LiveData<Result<CategoriesRemote>> {
        return recipeApiService.getCategoriesRemote()
    }

    private fun fetchLocalData(): List<CategoryDomain> {
        return categoryLocalDataSource.load()
    }
}