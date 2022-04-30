package com.example.recipes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.domain.interactors.RecipeFeedInteractor

class RecipeFeedViewModel(private val recipeFeedInteractor: RecipeFeedInteractor) : ViewModel() {
    companion object {
        private val TAG = RecipeFeedViewModel::class.java.simpleName
    }

    private val mutableRecipeFeed = MutableLiveData<List<RecipeFeedDomain>>()
    val recipeFeed: LiveData<List<RecipeFeedDomain>>
        get() = mutableRecipeFeed

    init {
        mutableRecipeFeed.value = recipeFeedInteractor.execute()
        Log.d(TAG, "${mutableRecipeFeed.value}")
    }
}

//class FeedViewModel1(private val recipeRepository: RecipeRepositoryImpl) : ViewModel() {
//    companion object {
//        private val TAG = FeedViewModel::class.java.simpleName
//    }
//
//    private val mutableRecipes = MutableLiveData<List<RecipeData>>()
//    val recipes: LiveData<List<RecipeData>>
//        get() = mutableRecipes
//
//    private val recipesFromRepository: LiveData<Result<List<RecipeData>>>
//        get() = recipeRepository.recipes
//
//    private val observer = Observer<Result<List<RecipeData>>> {
//        // как обработать it.isFailure?
//        mutableRecipes.value = it.getOrNull()
//    }
//
//    init {
//        recipesFromRepository.observeForever(observer)
//    }
//
//    override fun onCleared() {
//        Log.d(TAG, "onCleared called")
//        recipesFromRepository.removeObserver(observer)
//        super.onCleared()
//    }
//}