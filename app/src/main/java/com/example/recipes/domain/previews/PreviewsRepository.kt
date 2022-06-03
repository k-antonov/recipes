package com.example.recipes.domain.previews

import androidx.lifecycle.LiveData

interface PreviewsRepository {
    fun fetchData(endpoint: String): LiveData<Result<List<PreviewDomain>>>
}