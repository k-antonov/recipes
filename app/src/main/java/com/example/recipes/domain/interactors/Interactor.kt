package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData

// наверное интеракторы тоже можно переписать короче,
// они делают одно и то же
interface Interactor<T> {
    fun execute(): LiveData<Result<List<T>>>
}