package com.example.recipes.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.domain.favorites.FavoritePreviewsInteractor
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePreviewsViewModel @Inject constructor(
    private val favoritePreviewsInteractor: FavoritePreviewsInteractor
) : BaseViewModel<PreviewDomain>(favoritePreviewsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            favoritePreviewsInteractor.execute()
        }

    init {
        fetch()
    }
}