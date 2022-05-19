package com.example.recipes.presentation.viewmodels.localdetails

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.LocalDetailsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class LocalDetailsViewModel(
    private val localDetailsInteractor: LocalDetailsInteractor,
    recipeId: Long
) : BaseViewModel<DetailDomain>(localDetailsInteractor) {
    override val liveDataFromInteractor: LiveData<Result<List<DetailDomain>>>
        get() = localDetailsInteractor.execute()

    init {
        localDetailsInteractor.recipeId = recipeId
        fetch()
    }
}