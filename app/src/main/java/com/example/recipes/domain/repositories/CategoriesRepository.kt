package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CategoryDomain

interface CategoriesRepository {
    fun fetchData(): LiveData<Result<List<CategoryDomain>>>
}