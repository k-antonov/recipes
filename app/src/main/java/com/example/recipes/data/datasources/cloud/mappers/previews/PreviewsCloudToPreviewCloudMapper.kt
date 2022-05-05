package com.example.recipes.data.datasources.cloud.mappers.previews

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.recipes.data.Mapper
import com.example.recipes.data.datasources.cloud.entities.PreviewCloud
import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud

// todo fix DRY and encapsulation violation
class PreviewsCloudToPreviewCloudMapper : Mapper<PreviewsCloud, List<PreviewCloud>> {
    override fun mapEntity(fromEntity: PreviewsCloud): List<PreviewCloud> {
        return fromEntity.previewsCloud
    }

    private fun mapToResult(result: Result<PreviewsCloud>): Result<List<PreviewCloud>> {
        return result.map { mapEntity(it) }
    }

    fun mapToLiveData(liveData: LiveData<Result<PreviewsCloud>>): LiveData<Result<List<PreviewCloud>>> {
        return Transformations.map(liveData) { mapToResult(it) }
    }
}