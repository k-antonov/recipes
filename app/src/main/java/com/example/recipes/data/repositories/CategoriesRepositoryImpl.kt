package com.example.recipes.data.repositories

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipes.MyApplication
import com.example.recipes.MyApplication.Companion.categoryLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.CategoriesRemote
import com.example.recipes.data.datasources.remote.mappers.CategoriesRemoteToCategoryDomainListMapper
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.repositories.CategoriesRepository
import com.example.recipes.utils.observeOnce

class CategoriesRepositoryImpl(private val apiService: RecipeApiService) : CategoriesRepository {

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
        return apiService.getCategoriesRemote()
    }

    private fun fetchLocalData(): List<CategoryDomain> {
        return categoryLocalDataSource.load()
    }
}