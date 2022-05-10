package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.interactors.Interactor

abstract class BaseViewModel<T>(private val interactor: Interactor<T>) : ViewModel() {

    protected abstract val liveDataFromInteractor: LiveData<Result<List<T>>>

    private val mutableUiState = MutableLiveData<UiState<T>>()
    val uiState: LiveData<UiState<T>>
        get() = mutableUiState

    private val observer = Observer<Result<List<T>>> { result ->

        result?.onSuccess {
            mutableUiState.value = UiState.Success(items = it)
        }
        result?.onFailure {
            mutableUiState.value = UiState.Failure(throwable = it)
        }

    }

    protected open fun fetch() {
        mutableUiState.value = UiState.Loading()
        liveDataFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        liveDataFromInteractor.removeObserver(observer)
        super.onCleared()
    }

    sealed class UiState<T> {
        class Loading<T>: UiState<T>()
        class Failure<T>(val throwable: Throwable) : UiState<T>()
        class Success<T>(val items: List<T>) : UiState<T>()
    }

}