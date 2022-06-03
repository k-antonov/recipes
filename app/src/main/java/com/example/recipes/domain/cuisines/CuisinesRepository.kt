package com.example.recipes.domain.cuisines

import androidx.lifecycle.LiveData

interface CuisinesRepository {
    fun fetchData(): LiveData<Result<List<CuisineDomain>>>
}