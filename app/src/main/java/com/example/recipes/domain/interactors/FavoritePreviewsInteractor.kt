package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.FavoritePreviewsRepository


class FavoritePreviewsInteractor(
    private val favoritePreviewsRepository: FavoritePreviewsRepository
) : Interactor<PreviewDomain> {

    override fun execute(): LiveData<Result<List<PreviewDomain>>> =
        favoritePreviewsRepository.fetchData()
}