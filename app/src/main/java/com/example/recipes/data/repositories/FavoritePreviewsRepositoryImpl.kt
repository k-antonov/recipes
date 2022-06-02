package com.example.recipes.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.datasources.local.localdatasources.RecipeLocalDataSource
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.FavoritePreviewsRepository

class FavoritePreviewsRepositoryImpl(
    private val recipeLocalDataSource: RecipeLocalDataSource
) : FavoritePreviewsRepository {

    override fun fetchData(): LiveData<Result<List<PreviewDomain>>> {
        val list = recipeLocalDataSource.getPreviewsFavorite()
        return if (list.isEmpty()) {
            MutableLiveData(Result.failure(Exception("Empty favorites list")))
        } else {
            MutableLiveData(Result.success(list))
        }
    }
}