package com.example.recipes.domain.previews

import com.example.recipes.domain.core.DomainEntity

data class PreviewDomain(
    override val id: Long,
    val name: String,
    val imageUrl: String
) : DomainEntity()
