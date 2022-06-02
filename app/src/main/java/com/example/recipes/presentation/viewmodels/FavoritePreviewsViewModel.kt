package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.FavoritePreviewsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePreviewsViewModel @Inject constructor(
    private val favoritePreviewsInteractor: FavoritePreviewsInteractor
) : BaseViewModel<PreviewDomain>(favoritePreviewsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = favoritePreviewsInteractor.execute()

    init {
        fetch()
    }
}