package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData

interface Interactor<T> {
    fun execute(): LiveData<Result<List<T>>>
}