package com.example.recipes.presentation.viewmodels.localpreviews

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.LocalPreviewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class LocalPreviewsViewModel(
    private val localPreviewsInteractor: LocalPreviewsInteractor
) : BaseViewModel<PreviewDomain>(localPreviewsInteractor) {

    override val liveDataFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = localPreviewsInteractor.execute()

    init {
        fetch()
    }
}