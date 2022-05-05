package com.example.recipes.presentation.viewmodels.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.CategoriesInteractor

class CategoriesViewModelFactory(
    private val categoriesInteractor: CategoriesInteractor,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            return CategoriesViewModel(categoriesInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")

    }
}