package com.example.recipes.domain.entities

data class RecipeDetailsDomain(
    val id: Int,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrl: String
)