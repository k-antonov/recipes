package com.example.recipes.data.previews.datasources.remote

import com.example.recipes.data.core.datasources.remote.BaseMapper
import com.example.recipes.domain.previews.PreviewDomain

class PreviewsRemoteToPreviewDomainListMapper :
    BaseMapper<PreviewsRemote, PreviewRemote, PreviewDomain>() {
    override fun mapToList(from: PreviewsRemote): List<PreviewRemote> {
        return from.previewsRemote
    }

    override fun mapEntity(from: PreviewRemote) =
        PreviewDomain(
            id = from.id.toLong(),
            name = from.name,
            imageUrl = from.imageUrl
        )
}