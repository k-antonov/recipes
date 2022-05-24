package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.DetailsInteractor

class DetailsViewModel(
    private val detailsInteractor: DetailsInteractor,
    recipeId: Long
) : BaseViewModel<DetailDomain>(detailsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<DetailDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            detailsInteractor.execute()
        }

    init {
        detailsInteractor.recipeId = recipeId
        fetch()
    }
}

class DetailsViewModelFactory(
    private val detailsInteractor: DetailsInteractor,
    private val recipeId: Long
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(detailsInteractor, recipeId) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}