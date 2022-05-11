package com.example.recipes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.interactors.Interactor

abstract class BaseViewModel<T>(private val interactor: Interactor<T>) : ViewModel() {

    protected val reloadTrigger = MutableLiveData<Boolean>()

    protected abstract val liveDataFromInteractor: LiveData<Result<List<T>>>

    private val mutableUiState = MutableLiveData<UiState<T>>()
    val uiState: LiveData<UiState<T>>
        get() = mutableUiState

    private val observer = Observer<Result<List<T>>> { result ->

        result?.onSuccess {
            mutableUiState.value = UiState.Success(items = it)
        }
        result?.onFailure {
            Log.d("BaseViewModel", "throwable: $it")
            mutableUiState.value = UiState.Failure(throwable = it)
        }

    }

    protected open fun fetch() {
        reload()
        mutableUiState.value = UiState.Loading()
        liveDataFromInteractor.observeForever(observer)
    }

    fun reload() {
        reloadTrigger.value = true
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