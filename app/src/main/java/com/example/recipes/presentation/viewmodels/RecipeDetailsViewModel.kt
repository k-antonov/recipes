package com.example.recipes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.RecipeDetailsDomain
import com.example.recipes.domain.interactors.RecipeDetailsInteractor

class RecipeDetailsViewModel(
    private val recipeDetailsInteractor: RecipeDetailsInteractor,
    private val recipeId: Int
) : ViewModel() {

    companion object {
        private val TAG = RecipeDetailsViewModel::class.java.simpleName
    }

    private val recipeDetailsFromInteractor: LiveData<Result<RecipeDetailsDomain>>
        get() = recipeDetailsInteractor.execute(recipeId)

    private val mutableRecipeDetails = MutableLiveData<RecipeDetailsDomain>()
    val recipeDetails: LiveData<RecipeDetailsDomain>
        get() = mutableRecipeDetails

    private val observer = Observer<Result<RecipeDetailsDomain>> { result ->
        result?.onSuccess {
            mutableRecipeDetails.value = it
        }
        result?.onFailure {
            Log.d(TAG, "alert dialog")
        }
    }

    init {
        recipeDetailsFromInteractor.observeForever(observer)
    }

    override fun onCleared() {
        recipeDetailsFromInteractor.removeObserver(observer)
        super.onCleared()
    }

}