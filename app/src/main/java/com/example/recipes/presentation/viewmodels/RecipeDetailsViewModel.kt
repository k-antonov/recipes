package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.recipes.domain.interactors.RecipeDetailsInteractor

class RecipeDetailsViewModel(private val recipeDetailsInteractor: RecipeDetailsInteractor) : ViewModel() {
    // todo переписать с использованием LiveData

    val recipeDetailsDomain = recipeDetailsInteractor.execute()
}

//class RecipeDetailsViewModel1(
//    private val repository: RecipeRepositoryImpl,
//    private val recipeId: Int
//) : ViewModel() {
//
//    val recipeFeedDomain: RecipeData
//        get() {
//            val result = repository.recipes.value
//            val list = result?.getOrNull()
//
//            if (list != null) {
//                return list.first { it.id == recipeId }
//            } else {
//                // todo переделать в AlertDialog, например
//                throw Exception("Result failed")
//            }
//        }
//}