package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.DetailDomain
import com.example.recipes.domain.repositories.DetailsRepository

class DetailsInteractor(
    private val detailsRepository: DetailsRepository,
    var recipeId: Long = 0
) : Interactor<DetailDomain> {

    override fun execute(): LiveData<Result<List<DetailDomain>>> {
        return detailsRepository.fetchData(recipeId)
    }

    fun changeFavoriteStatus(recipeId: Long, isFavorite: Boolean) {
        detailsRepository.changeFavoriteStatus(recipeId, isFavorite)
    }
}