package com.example.recipes.domain.categories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.core.Interactor

class CategoriesInteractor(private val categoriesRepository: CategoriesRepository) :
    Interactor<CategoryDomain> {

    override fun execute(): LiveData<Result<List<CategoryDomain>>> {
        return categoriesRepository.fetchData()
    }
}