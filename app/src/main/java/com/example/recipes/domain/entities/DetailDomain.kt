package com.example.recipes.domain.entities

data class DetailDomain(
    val id: String,
    val name: String,
    val nameCategory: String,
    val nameCuisine: String,
    val strInstructions: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val measures: List<String>
)