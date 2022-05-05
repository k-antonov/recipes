package com.example.recipes.data.datasources.cloud.mappers.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.CategoriesCloud
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud

class CategoriesCloudToCategoryCloudMapper : Mapper<CategoriesCloud, List<CategoryCloud>> {
    override fun mapEntity(fromEntity: CategoriesCloud): List<CategoryCloud> {
        return fromEntity.categoriesCloud
    }

    private fun mapToResult(result: Result<CategoriesCloud>): Result<List<CategoryCloud>> {
        return result.map { mapEntity(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<CategoriesCloud>>): LiveData<Result<List<CategoryCloud>>> {
        return Transformations.map(liveData) { mapToResult(it) }
    }

}