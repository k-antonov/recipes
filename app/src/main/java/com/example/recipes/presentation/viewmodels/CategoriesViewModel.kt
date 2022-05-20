package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.interactors.CategoriesInteractor
import com.example.recipes.presentation.ui.categoriesInteractor

class CategoriesViewModel(
    categoriesInteractor: CategoriesInteractor
) : BaseViewModel<CategoryDomain>(categoriesInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<CategoryDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            categoriesInteractor.execute()
        }

    init {
        fetch()
    }
}

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