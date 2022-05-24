package com.example.recipes.domain.repositories

import androidx.lifecycle.LiveData
import com.example.recipes.domain.entities.CuisineDomain

interface CuisinesRepository {
    fun fetchData(): LiveData<Result<List<CuisineDomain>>>
}