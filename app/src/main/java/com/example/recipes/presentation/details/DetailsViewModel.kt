package com.example.recipes.presentation.details

import androidx.lifecycle.*
import com.example.recipes.domain.details.DetailDomain
import com.example.recipes.domain.details.DetailsInteractor
import com.example.recipes.presentation.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailDomain>(detailsInteractor) {

    val recipeId = savedStateHandle.get<Long>(DetailsFragment.ARG_RECIPE_ID)!!

    override val liveDataFromInteractor: LiveData<Result<List<DetailDomain>>>
        get() = Transformations.switchMap(reloadTrigger) {
            detailsInteractor.execute()
        }

    init {
        detailsInteractor.recipeId = recipeId
        fetch()
    }

    fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        detailsInteractor.changeFavoriteStatus(recipeId, isFavorite)
    }
}


