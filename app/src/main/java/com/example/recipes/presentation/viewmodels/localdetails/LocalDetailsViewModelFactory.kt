package com.example.recipes.presentation.viewmodels.localdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.LocalDetailsInteractor

class LocalDetailsViewModelFactory(
    private val localDetailsInteractor: LocalDetailsInteractor,
    private val recipeId: Long
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalDetailsViewModel::class.java)) {
            return LocalDetailsViewModel(localDetailsInteractor, recipeId) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}