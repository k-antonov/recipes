package com.example.recipes.presentation.previews

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.domain.previews.PreviewsInteractor
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
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
