package com.example.recipes.domain.previews

import androidx.lifecycle.LiveData
import com.example.recipes.domain.core.Interactor

class PreviewsInteractor(
    private val previewsRepository: PreviewsRepository,
    var endpoint: String = ""
) : Interactor<PreviewDomain> {

    override fun execute(): LiveData<Result<List<PreviewDomain>>> {
        return previewsRepository.fetchData(endpoint)
    }
}