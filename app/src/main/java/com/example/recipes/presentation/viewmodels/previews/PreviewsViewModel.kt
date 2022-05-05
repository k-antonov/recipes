package com.example.recipes.presentation.viewmodels.previews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.interactors.PreviewsInteractor

class PreviewsViewModel(
    private val previewsInteractor: PreviewsInteractor,
    private val endpoint: String
) : ViewModel() {
    companion object {
        private val TAG = PreviewsViewModel::class.java.simpleName
    }

    private val previewDomainListFromInteractor: LiveData<Result<List<PreviewDomain>>>
        get() = previewsInteractor.execute(endpoint)

    private val mutablePreviewDomainList = MutableLiveData<List<PreviewDomain>>()
    val previewDomainList: LiveData<List<PreviewDomain>>
        get() = mutablePreviewDomainList

    private val observer = Observer<Result<List<PreviewDomain>>> { result ->
        result?.onSuccess {
            mutablePreviewDomainList.value = it
        }
        result?.onFailure {
            Log.d(TAG, "alert dialog")
        }
    }

    init {
        previewDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        previewDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}