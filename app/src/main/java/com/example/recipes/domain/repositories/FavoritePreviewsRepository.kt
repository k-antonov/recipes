package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain

interface FavoritePreviewsRepository {
    fun fetchData(): LiveData<Result<List<PreviewDomain>>>
}