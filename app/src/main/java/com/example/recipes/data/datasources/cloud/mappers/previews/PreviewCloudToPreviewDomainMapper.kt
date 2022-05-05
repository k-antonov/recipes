package com.example.recipes.data.datasources.cloud.mappers.previews

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.PreviewCloud
import com.example.recipes.domain.entities.PreviewDomain

// todo fix DRY and encapsulation violation
class PreviewCloudToPreviewDomainMapper : Mapper<PreviewCloud, PreviewDomain> {
    override fun mapEntity(fromEntity: PreviewCloud): PreviewDomain {
        return PreviewDomain(
            id = fromEntity.id.toInt(),
            title = fromEntity.title,
            imageUrl = fromEntity.imageUrl
        )
    }

    private fun mapToList(fromEntities: List<PreviewCloud>): List<PreviewDomain> {
        return fromEntities.map { mapEntity(it) }
    }

    private fun mapToResult(result: Result<List<PreviewCloud>>): Result<List<PreviewDomain>> {
        return result.map { mapToList(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<List<PreviewCloud>>>): LiveData<Result<List<PreviewDomain>>> {
        return Transformations.map(liveData) { mapToResult(it) }
    }
}