package com.example.recipes.domain.categories

import com.example.recipes.domain.core.DomainEntity

data class CategoryDomain(
    override val id: Long,
    val name: String,
    val imageUrl: String
) : DomainEntity()
