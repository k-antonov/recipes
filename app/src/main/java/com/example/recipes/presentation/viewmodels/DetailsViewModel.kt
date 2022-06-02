package com.example.recipes.presentation.viewmodels

import androidx.lifecycle.*
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.interactors.DetailsInteractor
import com.example.recipes.presentation.ui.search.DetailsFragment
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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


