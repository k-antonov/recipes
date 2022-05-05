package com.example.recipes.presentation.viewmodels.cuisines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.interactors.CategoriesInteractor
import com.example.recipes.domain.interactors.CuisinesInteractor
import com.example.recipes.presentation.viewmodels.categories.CategoriesViewModel

class CuisinesViewModel(
    private val cuisinesInteractor: CuisinesInteractor
) : ViewModel() {
    companion object {
        private val TAG = CuisinesViewModel::class.java.simpleName
    }

    private val cuisineDomainListFromInteractor: LiveData<Result<List<CuisineDomain>>>
        get() = cuisinesInteractor.execute()

    private val mutableCuisineDomainList = MutableLiveData<List<CuisineDomain>>()
    val cuisineDomainList: LiveData<List<CuisineDomain>>
        get() = mutableCuisineDomainList

    private val observer = Observer<Result<List<CuisineDomain>>> { result ->
        result?.onSuccess {
            mutableCuisineDomainList.value = it
        }
        result?.onFailure {
            Log.d(TAG, "alert dialog")
        }
    }

    init {
        cuisineDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        cuisineDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}