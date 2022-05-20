package com.example.recipes.data.datasources.remote.entities
import com.squareup.moshi.Json


data class CuisinesRemote(
    @Json(name = "meals")
    val cuisinesRemote: List<CuisineRemote>
)

data class CuisineRemote(
    @Json(name = "strArea")
    val name: String
)