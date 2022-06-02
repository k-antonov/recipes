package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.interactors.CuisinesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CuisinesViewModel @Inject constructor(
    private val cuisinesInteractor: CuisinesInteractor
) : BaseViewModel<CuisineDomain>(cuisinesInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<CuisineDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            cuisinesInteractor.execute()
        }

    init {
        fetch()
    }
}
