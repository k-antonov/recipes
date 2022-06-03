package com.example.recipes.presentation.cuisines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.cuisines.CuisineDomain
import com.example.recipes.domain.cuisines.CuisinesInteractor
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
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
