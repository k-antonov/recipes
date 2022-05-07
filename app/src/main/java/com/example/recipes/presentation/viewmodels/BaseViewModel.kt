package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.interactors.Interactor

abstract class BaseViewModel<T>(private val interactor: Interactor<T>) : ViewModel() {

    protected open val itemDomainListFromInteractor: LiveData<Result<List<T>>>
        get() = interactor.execute()

    private val mutableItemDomainList = MutableLiveData<List<T>>()
    val itemDomainList: LiveData<List<T>>
        get() = mutableItemDomainList

    protected val observer = Observer<Result<List<T>>> { result ->
        result?.onSuccess {
            mutableItemDomainList.value = it
        }
        result?.onFailure {

        }
    }
}