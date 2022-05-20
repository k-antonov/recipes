package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.PreviewsInteractor

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

class PreviewsViewModelFactory(
    private val previewsInteractor: PreviewsInteractor,
    private val endpoint: String
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreviewsViewModel::class.java)) {
            return PreviewsViewModel(previewsInteractor, endpoint) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}
