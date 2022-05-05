package com.example.recipes.presentation.viewmodels.cuisines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipes.domain.interactors.CuisinesInteractor

class CuisinesViewModelFactory(
    private val cuisinesInteractor: CuisinesInteractor,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuisinesViewModel::class.java)) {
            return CuisinesViewModel(cuisinesInteractor) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")

    }
}