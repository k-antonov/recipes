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
            // Здесь вносится id, name и imageUrl без categoryId или cuisineId,
            // поэтому локально отобразить превью без всей информации о рецепте не получится
            // (запрос делается с условием по категории или кухне). Поэтому, возможно, нет смысла
            // вносить превью в БД. Даже если бы я вносил превью с категорией/кухней, то при
            // открытии списка превью пользователь, вероятно, хочет иметь возможность
            // получить всю информацию о рецепте, а не только картинку с названием и надпись
            // "Network Error" при нажатии на неё. Что думаешь?
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
        val categoryOrCuisineName = getCategoryOrCuisineName(endpoint)
        return recipeLocalDataSource.loadPreviewsByCategoryOrCuisine(categoryOrCuisineName)
    }

    // приходит в виде "c=Pork" или "a=Dutch"
    private fun getCategoryOrCuisineName(endpoint: String): String {
        return endpoint.slice(2..endpoint.lastIndex)
    }
}