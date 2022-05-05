package com.example.recipes.presentation.viewmodels.previews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.PreviewsInteractor

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