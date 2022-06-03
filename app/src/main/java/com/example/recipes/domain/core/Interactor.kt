package com.example.recipes.domain.core

import androidx.lifecycle.LiveData

interface Interactor<T> {
    fun execute(): LiveData<Result<List<T>>>
}