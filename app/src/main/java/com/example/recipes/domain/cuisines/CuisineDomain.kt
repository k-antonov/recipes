package com.example.recipes.domain.cuisines

import com.example.recipes.domain.core.DomainEntity

data class CuisineDomain(
    override val id: Long,
    val name: String,
    val imageUrl: String
) : DomainEntity()