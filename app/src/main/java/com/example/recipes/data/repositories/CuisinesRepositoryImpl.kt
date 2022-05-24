package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipes.MyApplication.Companion.cuisineLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.CuisinesRemote
import com.example.recipes.data.datasources.remote.mappers.CuisinesRemoteToCuisineDomainListMapper
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.repositories.CuisinesRepository
import com.example.recipes.utils.observeOnce

class CuisinesRepositoryImpl(private val apiService: RecipeApiService) : CuisinesRepository {

    private val mapper: CuisinesRemoteToCuisineDomainListMapper by lazy { CuisinesRemoteToCuisineDomainListMapper() }

    private val observer = Observer<Result<List<CuisineDomain>>> { result ->
        result.onSuccess {
            cuisineLocalDataSource.insertList(it)
        }
    }

    override fun fetchData(): LiveData<Result<List<CuisineDomain>>> {
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

    private fun fetchRemoteData(): LiveData<Result<CuisinesRemote>> {
        return apiService.getCuisinesRemote()
    }

    private fun fetchLocalData(): List<CuisineDomain> {
        return cuisineLocalDataSource.load()
    }

}