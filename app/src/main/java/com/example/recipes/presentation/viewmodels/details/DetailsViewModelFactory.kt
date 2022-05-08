package com.example.recipes.presentation.viewmodels.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.DetailsInteractor

class DetailsViewModelFactory(
    private val detailsInteractor: DetailsInteractor,
    private val endpoint: String
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(detailsInteractor, endpoint) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}