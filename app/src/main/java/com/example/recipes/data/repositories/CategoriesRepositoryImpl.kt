package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipes.MyApplication.Companion.categoryLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.CategoriesRemote
import com.example.recipes.data.datasources.remote.mappers.CategoriesRemoteToCategoryDomainListMapper
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.repositories.CategoriesRepository
import com.example.recipes.utils.observeOnce

class CategoriesRepositoryImpl(private val apiService: RecipeApiService) : CategoriesRepository {

    private val mapper: CategoriesRemoteToCategoryDomainListMapper by lazy { CategoriesRemoteToCategoryDomainListMapper() }

    private val observer = Observer<Result<List<CategoryDomain>>> { result ->
        result.onSuccess {
            categoryLocalDataSource.insertList(it)
        }
    }

    override fun fetchData(): LiveData<Result<List<CategoryDomain>>> {
        val localDataList = fetchLocalData()
        return if (localDataList.isEmpty()) {
            val remoteDataList = fetchRemoteData()
            val mappedToDomain = mapper.mapLiveData(remoteDataList)
            mappedToDomain.observeOnce(observer)
            mappedToDomain
        } else {
            MutableLiveData(Result.success(localDataList))
        }
    }

    private fun fetchRemoteData(): LiveData<Result<CategoriesRemote>> {
        return apiService.getCategoriesRemote()
    }

    private fun fetchLocalData(): List<CategoryDomain> {
        return categoryLocalDataSource.load()
    }
}