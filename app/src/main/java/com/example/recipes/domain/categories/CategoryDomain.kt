package com.example.recipes.domain.categories

import com.example.recipes.domain.core.DomainEntity

data class CategoryDomain(
    val id: Long,
    override val name: String,
    val imageUrl: String
) : DomainEntity()
