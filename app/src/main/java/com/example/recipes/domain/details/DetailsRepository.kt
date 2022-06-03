package com.example.recipes.domain.details

import androidx.lifecycle.LiveData

interface DetailsRepository {
    fun fetchData(recipeId: Long): LiveData<Result<List<DetailDomain>>>
    fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean)
}