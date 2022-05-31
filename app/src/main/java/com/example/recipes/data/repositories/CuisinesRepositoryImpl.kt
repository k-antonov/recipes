package com.example.recipes.data.repositories

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.MyApplication.Companion.cuisineLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.CuisinesRemote
import com.example.recipes.data.datasources.remote.mappers.CuisinesRemoteToCuisineDomainListMapper
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.repositories.CuisinesRepository
import com.example.recipes.utils.observeOnce

class CuisinesRepositoryImpl(private val apiService: RecipeApiService) : CuisinesRepository {

    private val mapper: CuisinesRemoteToCuisineDomainListMapper by lazy { CuisinesRemoteToCuisineDomainListMapper() }

    private val liveDataToReturn = MutableLiveData<Result<List<CuisineDomain>>>()

    override fun fetchData(): LiveData<Result<List<CuisineDomain>>> {
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
                        cuisineLocalDataSource.insertList(it)
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

    private fun fetchRemoteData(): LiveData<Result<CuisinesRemote>> {
        return apiService.getCuisinesRemote()
    }

    private fun fetchLocalData(): List<CuisineDomain> {
        return cuisineLocalDataSource.load()
    }

}