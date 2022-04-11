package com.example.recipes.data.model

import com.squareup.moshi.Json

data class Recipe(
    @Json(name="id")
    val id: Int,
    @Json(name="title")
    val title: String,
    @Json(name="ingredients")
    val ingredients: List<String>,
    @Json(name="instructions")
    val instructions: List<Instruction>,
    @Json(name="times")
    val times: List<String>,
    @Json(name="image")
    val imageUrl: String
)
