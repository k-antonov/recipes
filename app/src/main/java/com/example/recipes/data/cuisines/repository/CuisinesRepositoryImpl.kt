package com.example.recipes.data.cuisines.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.core.datasources.remote.RecipeApiService
import com.example.recipes.data.cuisines.datasources.local.CuisineLocalDataSource
import com.example.recipes.data.cuisines.datasources.remote.CuisinesRemote
import com.example.recipes.data.cuisines.datasources.remote.CuisinesRemoteToCuisineDomainListMapper
import com.example.recipes.domain.cuisines.CuisineDomain
import com.example.recipes.domain.cuisines.CuisinesRepository
import com.example.recipes.data.core.repository.observeOnce

class CuisinesRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val cuisineLocalDataSource: CuisineLocalDataSource
) : CuisinesRepository {

    private val mapper: CuisinesRemoteToCuisineDomainListMapper by lazy { CuisinesRemoteToCuisineDomainListMapper() }

    private val liveDataToReturn = MutableLiveData<Result<List<CuisineDomain>>>()

    override fun fetchData(): LiveData<Result<List<CuisineDomain>>> {
        val localDataList = fetchLocalData()
        if (localDataList.isNotEmpty()) {
            liveDataToReturn.value = Result.success(localDataList)
        }

        val remoteDataList = fetchRemoteData()
        val mappedToDomain = mapper.mapLiveData(remoteDataList)
        mappedToDomain.observeOnce { result ->
            result.onSuccess {
                liveDataToReturn.value = Result.success(it)
                cuisineLocalDataSource.insertList(it)
            }
            result.onFailure {
                if (localDataList.isEmpty()) {
                    liveDataToReturn.value =
                        Result.failure(NetworkErrorException("Network error"))
                }
            }
        }

        return liveDataToReturn
    }

    private fun fetchRemoteData(): LiveData<Result<CuisinesRemote>> {
        return recipeApiService.getCuisinesRemote()
    }

    private fun fetchLocalData(): List<CuisineDomain> {
        return cuisineLocalDataSource.load()
    }

}