package com.example.recipes.presentation.viewmodels.previews

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.PreviewsInteractor
import com.example.recipes.presentation.viewmodels.BaseViewModel

class PreviewsViewModel(
    private val previewsInteractor: PreviewsInteractor,
    private val endpoint: String
) : BaseViewModel<PreviewDomain>(previewsInteractor) {

    override val itemDomainListFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() {
            previewsInteractor.endpoint = endpoint
            return previewsInteractor.execute()
        }

    init {
        itemDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        itemDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}
