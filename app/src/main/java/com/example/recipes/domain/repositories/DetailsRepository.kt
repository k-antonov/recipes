package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.DetailDomain

interface DetailsRepository {
    fun fetchData(recipeId: Long): LiveData<Result<List<DetailDomain>>>
    fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean)
}