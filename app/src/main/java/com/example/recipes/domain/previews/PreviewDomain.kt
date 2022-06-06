package com.example.recipes.domain.previews

import com.example.recipes.domain.core.DomainEntity

data class PreviewDomain(
    val id: Long,
    override val name: String,
    val imageUrl: String
) : DomainEntity()
