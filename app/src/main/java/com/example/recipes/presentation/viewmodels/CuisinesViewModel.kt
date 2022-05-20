package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.interactors.CuisinesInteractor
import com.example.recipes.presentation.ui.cuisinesInteractor

class CuisinesViewModel(
    cuisinesInteractor: CuisinesInteractor
) : BaseViewModel<CuisineDomain>(cuisinesInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<CuisineDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            cuisinesInteractor.execute()
        }

    init {
        fetch()
    }
}

class CuisinesViewModelFactory(
    private val cuisinesInteractor: CuisinesInteractor,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuisinesViewModel::class.java)) {
            return CuisinesViewModel(cuisinesInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")

    }
}