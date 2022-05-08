package com.example.recipes.data.datasources.cloud.entities
import com.squareup.moshi.Json


data class CuisinesCloud(
    @Json(name = "meals")
    val cuisinesCloud: List<CuisineCloud>
)

data class CuisineCloud(
    @Json(name = "strArea")
    val name: String
)