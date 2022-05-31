package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.FavoritePreviewsInteractor

class FavoritePreviewsViewModel(
    private val favoritePreviewsInteractor: FavoritePreviewsInteractor
) : BaseViewModel<PreviewDomain>(favoritePreviewsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = favoritePreviewsInteractor.execute()

    init {
        fetch()
    }
}

class FavoritePreviewsViewModelFactory(
    private val favoritePreviewsInteractor: FavoritePreviewsInteractor,
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritePreviewsViewModel::class.java)) {
            return FavoritePreviewsViewModel(favoritePreviewsInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}