package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.PreviewsInteractor
import com.example.recipes.presentation.ui.search.PreviewsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewsViewModel @Inject constructor(
    private val previewsInteractor: PreviewsInteractor,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<PreviewDomain>(previewsInteractor) {

    private val endpoint = savedStateHandle.get<String>(PreviewsFragment.ARG_ENDPOINT)!!

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            previewsInteractor.execute()
        }

    init {
        previewsInteractor.endpoint = endpoint
        fetch()
    }

}
