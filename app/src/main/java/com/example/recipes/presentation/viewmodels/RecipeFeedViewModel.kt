package com.example.recipes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.interactors.RecipeFeedInteractor

class RecipeFeedViewModel(private val recipeFeedInteractor: RecipeFeedInteractor) : ViewModel() {
    companion object {
        private val TAG = RecipeFeedViewModel::class.java.simpleName
    }

    private val recipeFeedFromInteractor: LiveData<Result<List<RecipeFeedDomain>>>
        get() = recipeFeedInteractor.execute()


    private val mutableRecipeFeed = MutableLiveData<List<RecipeFeedDomain>>()
    val recipeFeed: LiveData<List<RecipeFeedDomain>>
        get() = mutableRecipeFeed

    private val observer = Observer<Result<List<RecipeFeedDomain>>> { result ->
        result?.onSuccess {
            mutableRecipeFeed.value = it
        }
        result?.onFailure {
            Log.d(TAG, "alert dialog")
        }
    }

    init {
        recipeFeedFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        recipeFeedFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}