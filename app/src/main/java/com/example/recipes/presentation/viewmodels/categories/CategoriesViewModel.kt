package com.example.recipes.presentation.viewmodels.categories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.interactors.CategoriesInteractor
import com.example.recipes.presentation.ui.categoriesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class CategoriesViewModel(
    categoriesInteractor: CategoriesInteractor
) : BaseViewModel<CategoryDomain>(categoriesInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<CategoryDomain>>>
        get() = categoriesInteractor.execute()

    init {
        fetch()
    }
}