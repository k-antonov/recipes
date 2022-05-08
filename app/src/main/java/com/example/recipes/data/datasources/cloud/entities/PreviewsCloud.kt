package com.example.recipes.data.datasources.cloud.entities
import com.squareup.moshi.Json


data class PreviewsCloud(
    @Json(name = "meals")
    val previewsCloud: List<PreviewCloud>
)

data class PreviewCloud(
    @Json(name = "idMeal")
    val id: String,
    @Json(name = "strMeal")
    val name: String,
    @Json(name = "strMealThumb")
    val imageUrl: String
)