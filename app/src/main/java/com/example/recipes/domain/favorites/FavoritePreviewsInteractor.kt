package com.example.recipes.domain.favorites

import androidx.lifecycle.LiveData
import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.domain.core.Interactor


class FavoritePreviewsInteractor(
    private val favoritePreviewsRepository: FavoritePreviewsRepository
) : Interactor<PreviewDomain> {

    override fun execute(): LiveData<Result<List<PreviewDomain>>> =
        favoritePreviewsRepository.fetchData()
}