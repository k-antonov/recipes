package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.domain.repositories.PreviewsRepository

class PreviewsInteractor(
    private val previewsRepository: PreviewsRepository,
    var endpoint: String = ""
) : Interactor<PreviewDomain> {

    override fun execute(): LiveData<Result<List<PreviewDomain>>> {
        return previewsRepository.fetchData(endpoint)
    }
}