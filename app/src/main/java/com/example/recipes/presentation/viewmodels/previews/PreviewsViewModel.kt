package com.example.recipes.presentation.viewmodels.previews

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.PreviewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class PreviewsViewModel(
    private val previewsInteractor: PreviewsInteractor,
    endpoint: String
) : BaseViewModel<PreviewDomain>(previewsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            previewsInteractor.execute()
        }

    init {
        previewsInteractor.endpoint = endpoint
        fetch()
    }

}
