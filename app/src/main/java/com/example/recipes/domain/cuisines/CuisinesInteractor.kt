package com.example.recipes.domain.cuisines

import androidx.lifecycle.LiveData
import com.example.recipes.domain.core.Interactor

class CuisinesInteractor(private val cuisinesRepository: CuisinesRepository) :
    Interactor<CuisineDomain> {

    override fun execute(): LiveData<Result<List<CuisineDomain>>> {
        return cuisinesRepository.fetchData()
    }
}