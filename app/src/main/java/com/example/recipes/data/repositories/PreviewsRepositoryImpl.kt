package com.example.recipes.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipes.MyApplication.Companion.recipeLocalDataSource
import com.example.recipes.data.datasources.remote.RecipeApiService
import com.example.recipes.data.datasources.remote.entities.PreviewsRemote
import com.example.recipes.data.datasources.remote.mappers.PreviewsRemoteToPreviewDomainListMapper
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.PreviewsRepository
import com.example.recipes.utils.observeOnce

class PreviewsRepositoryImpl(private val apiService: RecipeApiService) : PreviewsRepository {

    private val mapper: PreviewsRemoteToPreviewDomainListMapper by lazy { PreviewsRemoteToPreviewDomainListMapper() }

    private val observer = Observer<Result<List<PreviewDomain>>> { result ->
        result.onSuccess {
            recipeLocalDataSource.insertPreviewList(it)
        }
    }

    override fun fetchData(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val localDataList = fetchLocalData(endpoint)
        Log.d("PreviewsRepository", "localDataList: $localDataList")
        return if (localDataList.isEmpty()) {
            val remoteDataList = fetchRemoteData(endpoint)
            val mappedToDomain = mapper.mapLiveData(remoteDataList)
            mappedToDomain.observeOnce(observer)
            mappedToDomain
        } else {
            MutableLiveData(Result.success(localDataList))
        }
    }

    private fun fetchRemoteData(endpoint: String): LiveData<Result<PreviewsRemote>> {
        return apiService.getPreviewsRemote(endpoint)
    }

    // endpoint - categoryName или cuisineName
    private fun fetchLocalData(endpoint: String): List<PreviewDomain> {
        // todo пофиксить
        val fixedEndpoint = endpoint.slice(2..endpoint.lastIndex)
        return recipeLocalDataSource.loadPreviewsByCategoryOrCuisine(fixedEndpoint)
    }
}