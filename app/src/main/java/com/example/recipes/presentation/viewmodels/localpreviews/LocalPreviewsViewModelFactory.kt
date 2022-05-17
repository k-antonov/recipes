package com.example.recipes.presentation.viewmodels.localpreviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.LocalPreviewsInteractor

class LocalPreviewsViewModelFactory(
    private val localPreviewsInteractor: LocalPreviewsInteractor,
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalPreviewsViewModel::class.java)) {
            return LocalPreviewsViewModel(localPreviewsInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}