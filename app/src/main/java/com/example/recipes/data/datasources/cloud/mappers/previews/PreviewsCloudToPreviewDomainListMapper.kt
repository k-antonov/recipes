package com.example.recipes.data.datasources.cloud.mappers.previews

import com.example.recipes.data.datasources.cloud.entities.PreviewCloud
import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud
import com.example.recipes.data.datasources.cloud.mappers.BaseMapper
import com.example.recipes.domain.entities.PreviewDomain

class PreviewsCloudToPreviewDomainListMapper :
    BaseMapper<PreviewsCloud, PreviewCloud, PreviewDomain>() {
    override fun mapToList(from: PreviewsCloud): List<PreviewCloud> {
        return from.previewsCloud
    }

    override fun mapEntity(from: PreviewCloud) =
        PreviewDomain(
            id = from.id.toInt(),
            title = from.title,
            imageUrl = from.imageUrl
        )
}