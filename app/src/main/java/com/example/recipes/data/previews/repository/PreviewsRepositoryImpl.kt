package com.example.recipes.data.previews.repository

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.details.datasources.local.localdatasources.RecipeLocalDataSource
import com.example.recipes.data.core.datasources.remote.RecipeApiService
import com.example.recipes.data.previews.datasources.remote.PreviewsRemote
import com.example.recipes.data.previews.datasources.remote.PreviewsRemoteToPreviewDomainListMapper
import com.example.recipes.data.core.repository.FetchDataCallback
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.domain.previews.PreviewsRepository
import com.example.recipes.data.core.repository.observeOnce

class PreviewsRepositoryImpl(
    private val recipeApiService: RecipeApiService,
    private val recipeLocalDataSource: RecipeLocalDataSource
) : PreviewsRepository {

    private val mapper: PreviewsRemoteToPreviewDomainListMapper by lazy { PreviewsRemoteToPreviewDomainListMapper() }

    private val liveDataToReturn = MutableLiveData<Result<List<PreviewDomain>>>()

    override fun fetchData(endpoint: String): LiveData<Result<List<PreviewDomain>>> {
        val localDataList = fetchLocalData(endpoint)
        Log.d("PreviewsRepository", "localDataList: $localDataList")

        if (localDataList.isNotEmpty()) {
            liveDataToReturn.value = Result.success(localDataList)
        }

        wrapFetchingRemoteData(object : FetchDataCallback {
            override fun onSuccess() {
                val remoteDataList = fetchRemoteData(endpoint)
                val mappedToDomain = mapper.mapLiveData(remoteDataList)
                mappedToDomain.observeOnce { result ->
                    result.onSuccess {
                        liveDataToReturn.value = Result.success(it)

                        // Здесь вносится id, name и imageUrl без categoryId или cuisineId,
                        // поэтому локально отобразить превью без всей информации о рецепте не получится
                        // (запрос делается с условием по категории или кухне). Поэтому, возможно, нет смысла
                        // вносить превью в БД. Даже если бы я вносил превью с категорией/кухней, то при
                        // открытии списка превью пользователь, вероятно, хочет иметь возможность
                        // получить всю информацию о рецепте, а не только картинку с названием и надпись
                        // "Network Error" при нажатии на неё. Что думаешь?
//                        recipeLocalDataSource.insertPreviewList(it)
                    }
                    result.onFailure {
                        Log.d("PreviewsRepo", "onFailure")
                        if (localDataList.isEmpty()) {
                            liveDataToReturn.value =
                                Result.failure(NetworkErrorException("Network error"))
                        }
                    }
                }
            }
        })

        Log.d("PreviewsRepo", "after callback: $localDataList")

        return liveDataToReturn
    }

    private fun wrapFetchingRemoteData(fetchDataCallback: FetchDataCallback) {
        fetchDataCallback.onSuccess()
    }

    private fun fetchRemoteData(endpoint: String): LiveData<Result<PreviewsRemote>> {
        return recipeApiService.getPreviewsRemote(endpoint)
    }

    // endpoint - categoryName или cuisineName
    private fun fetchLocalData(endpoint: String): List<PreviewDomain> {
        val categoryOrCuisineName = getCategoryOrCuisineName(endpoint)
        return recipeLocalDataSource.loadPreviewsByCategoryOrCuisine(categoryOrCuisineName)
    }

    // приходит в виде "c=Pork" или "a=Dutch"
    private fun getCategoryOrCuisineName(endpoint: String): String {
        return endpoint.slice(2..endpoint.lastIndex)
    }
}