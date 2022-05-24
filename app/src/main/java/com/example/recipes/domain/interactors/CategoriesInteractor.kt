package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.repositories.CategoriesRepository

class CategoriesInteractor(private val categoriesRepository: CategoriesRepository) : Interactor<CategoryDomain> {

    override fun execute(): LiveData<Result<List<CategoryDomain>>> {
        return categoriesRepository.fetchData()
    }
}