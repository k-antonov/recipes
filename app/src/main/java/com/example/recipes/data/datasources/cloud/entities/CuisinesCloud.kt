package com.example.recipes.data.datasources.cloud.entities
import com.squareup.moshi.Json


data class CuisinesCloud(
    @Json(name = "meals")
    val cuisineClouds: List<CuisineCloud>
)

data class CuisineCloud(
    @Json(name = "strArea")
    val title: String
)