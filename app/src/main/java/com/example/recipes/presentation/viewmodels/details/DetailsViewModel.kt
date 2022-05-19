package com.example.recipes.presentation.viewmodels.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.DetailsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class DetailsViewModel(
    private val detailsInteractor: DetailsInteractor,
    endpoint: String
) : BaseViewModel<DetailDomain>(detailsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<DetailDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            detailsInteractor.execute()
        }

    init {
        detailsInteractor.endpoint = endpoint
        fetch()
    }
}