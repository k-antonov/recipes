package com.example.recipes.domain.favorites

import androidx.lifecycle.LiveData
import com.example.recipes.domain.previews.PreviewDomain

interface FavoritePreviewsRepository {
    fun fetchData(): LiveData<Result<List<PreviewDomain>>>
}