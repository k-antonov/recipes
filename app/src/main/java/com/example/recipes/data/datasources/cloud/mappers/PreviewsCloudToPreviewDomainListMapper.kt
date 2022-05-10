package com.example.recipes.data.datasources.cloud.mappers

import com.example.recipes.data.datasources.cloud.entities.PreviewCloud
import com.example.recipes.data.datasources.cloud.entities.PreviewsCloud
import com.example.recipes.domain.entities.PreviewDomain

class PreviewsCloudToPreviewDomainListMapper :
    BaseMapper<PreviewsCloud, PreviewCloud, PreviewDomain>() {
    override fun mapToList(from: PreviewsCloud): List<PreviewCloud> {
        return from.previewsCloud
    }

    override fun mapEntity(from: PreviewCloud) =
        PreviewDomain(
            id = from.id,
            name = from.name,
            imageUrl = from.imageUrl
        )
}