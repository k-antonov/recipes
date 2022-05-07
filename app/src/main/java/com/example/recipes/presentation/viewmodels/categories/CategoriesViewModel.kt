package com.example.recipes.presentation.viewmodels.categories

import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.interactors.CategoriesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class CategoriesViewModel(
    categoriesInteractor: CategoriesInteractor
) : BaseViewModel<CategoryDomain>(categoriesInteractor) {

    init {
        itemDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        itemDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}