package com.example.recipes.data.datasources.remote.mappers

import com.example.recipes.data.datasources.remote.entities.PreviewRemote
import com.example.recipes.data.datasources.remote.entities.PreviewsRemote
import com.example.recipes.domain.entities.PreviewDomain

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