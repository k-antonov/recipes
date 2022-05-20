package com.example.recipes.data.datasources.remote.entities
import com.squareup.moshi.Json


data class PreviewsRemote(
    @Json(name = "meals")
    val previewsRemote: List<PreviewRemote>
)

data class PreviewRemote(
    @Json(name = "idMeal")
    val id: String,
    @Json(name = "strMeal")
    val name: String,
    @Json(name = "strMealThumb")
    val imageUrl: String
)