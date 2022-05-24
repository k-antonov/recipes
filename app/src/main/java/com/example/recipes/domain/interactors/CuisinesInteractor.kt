package com.example.recipes.domain.interactors

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.domain.repositories.CuisinesRepository

class CuisinesInteractor(private val cuisinesRepository: CuisinesRepository) : Interactor<CuisineDomain> {

    override fun execute(): LiveData<Result<List<CuisineDomain>>> {
        return cuisinesRepository.fetchData()
    }
}