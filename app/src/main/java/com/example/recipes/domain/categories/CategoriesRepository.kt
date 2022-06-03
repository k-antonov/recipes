package com.example.recipes.domain.categories

import androidx.lifecycle.LiveData

interface CategoriesRepository {
    fun fetchData(): LiveData<Result<List<CategoryDomain>>>
}