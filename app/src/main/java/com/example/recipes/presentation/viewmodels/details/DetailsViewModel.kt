package com.example.recipes.presentation.viewmodels.details

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.DetailsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class DetailsViewModel(
    private val detailsInteractor: DetailsInteractor,
    private val endpoint: String
) : BaseViewModel<DetailDomain>(detailsInteractor) {

    override val itemDomainListFromInteractor: LiveData<Result<List<DetailDomain>>>
        get() {
            detailsInteractor.endpoint = endpoint
            return detailsInteractor.execute()
        }

    init {
        itemDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        itemDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}