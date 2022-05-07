package com.example.recipes.presentation.viewmodels.cuisines

import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.interactors.CuisinesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class CuisinesViewModel(
    cuisinesInteractor: CuisinesInteractor
) : BaseViewModel<CuisineDomain>(cuisinesInteractor) {

    init {
        itemDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        itemDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}