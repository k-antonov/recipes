package com.example.recipes.domain.details

import com.example.recipes.domain.core.DomainEntity

data class DetailDomain(
    val id: Long,
    override val name: String,
    val nameCategory: String,
    val nameCuisine: String,
    val strInstructions: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val measures: List<String>,
    var isFavorite: Boolean
) : DomainEntity()