package com.example.recipes.data.datasources.cloud.mappers.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.CategoryCloud
import com.example.recipes.domain.entities.CategoryDomain

// todo fix DRY violation
class CategoryCloudToCategoryDomainMapper : Mapper<CategoryCloud, CategoryDomain> {
    // todo fix encapsulation violation
    override fun mapEntity(fromEntity: CategoryCloud): CategoryDomain {
        return CategoryDomain(
            id = fromEntity.id.toInt(),
            title = fromEntity.title,
            imageUrl = fromEntity.imageUrl
        )
    }

    private fun mapToList(fromEntities: List<CategoryCloud>): List<CategoryDomain> {
        return fromEntities.map { mapEntity(it) }
    }

    private fun mapToResult(result: Result<List<CategoryCloud>>): Result<List<CategoryDomain>> {
        return result.map { mapToList(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<List<CategoryCloud>>>): LiveData<Result<List<CategoryDomain>>> {
        return Transformations.map(liveData) { mapToResult(it) }
    }
}