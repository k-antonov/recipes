package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.LocalDetailsInteractor

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

class LocalDetailsViewModelFactory(
    private val localDetailsInteractor: LocalDetailsInteractor,
    private val recipeId: Long
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalDetailsViewModel::class.java)) {
            return LocalDetailsViewModel(localDetailsInteractor, recipeId) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}