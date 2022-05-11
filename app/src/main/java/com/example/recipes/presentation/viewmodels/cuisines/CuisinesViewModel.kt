package com.example.recipes.presentation.viewmodels.cuisines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.interactors.CuisinesInteractor
import com.example.recipes.presentation.ui.cuisinesInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class CuisinesViewModel(
    cuisinesInteractor: CuisinesInteractor
) : BaseViewModel<CuisineDomain>(cuisinesInteractor) {

//    override val liveDataFromInteractor: LiveData<Result<List<CuisineDomain>>>
//        get() = cuisinesInteractor.execute()

    override val liveDataFromInteractor: LiveData<Result<List<CuisineDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            cuisinesInteractor.execute()
        }

    init {
        fetch()
    }
}