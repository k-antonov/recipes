package com.example.recipes.data.entities

import com.squareup.moshi.Json

data class RecipeData(
    @Json(name="id")
    val id: Int,
    @Json(name="title")
    val title: String,
    @Json(name="ingredients")
    val ingredients: List<String>,
    @Json(name="instructions")
    val instructionData: List<InstructionData>,
    @Json(name="times")
    val times: List<String>,
    @Json(name="image")
    val imageUrl: String
)
