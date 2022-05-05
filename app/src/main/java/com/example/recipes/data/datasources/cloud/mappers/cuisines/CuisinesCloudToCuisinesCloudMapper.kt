package com.example.recipes.data.datasources.cloud.mappers.cuisines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.CuisineCloud
import com.example.recipes.data.datasources.cloud.entities.CuisinesCloud

// todo fix DRY and encapsulation violation
class CuisinesCloudToCuisinesCloudMapper : Mapper<CuisinesCloud, List<CuisineCloud>> {
    override fun mapEntity(fromEntity: CuisinesCloud): List<CuisineCloud> {
        return fromEntity.cuisineClouds
    }

    private fun mapToResult(result: Result<CuisinesCloud>): Result<List<CuisineCloud>> {
        return result.map { mapEntity(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<CuisinesCloud>>): LiveData<Result<List<CuisineCloud>>> {
        return Transformations.map(liveData) { mapToResult(it) }
    }
}