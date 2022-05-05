package com.example.recipes.presentation.viewmodels.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.interactors.CategoriesInteractor

class CategoriesViewModel(
    private val categoriesInteractor: CategoriesInteractor
) : ViewModel() {
    companion object {
        private val TAG = CategoriesViewModel::class.java.simpleName
    }

    private val categoryDomainListFromInteractor: LiveData<Result<List<CategoryDomain>>>
        get() = categoriesInteractor.execute()

    private val mutableCategoryDomainList = MutableLiveData<List<CategoryDomain>>()
    val categoryDomainList: LiveData<List<CategoryDomain>>
        get() = mutableCategoryDomainList

    private val observer = Observer<Result<List<CategoryDomain>>> { result ->
        result?.onSuccess {
            mutableCategoryDomainList.value = it
        }
        result?.onFailure {
            Log.d(TAG, "alert dialog")
        }
    }

    init {
        categoryDomainListFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        categoryDomainListFromInteractor.removeObserver(observer)
        super.onCleared()
    }
}