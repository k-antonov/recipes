package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain

interface PreviewsRepository {
    fun fetchData(endpoint: String): LiveData<Result<List<PreviewDomain>>>
}