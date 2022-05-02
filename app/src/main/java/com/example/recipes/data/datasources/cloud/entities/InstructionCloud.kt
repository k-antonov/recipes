package com.example.recipes.data.datasources.cloud.entities

import com.squareup.moshi.Json

data class InstructionCloud(
    @Json(name="type")
    val type: String?,
    @Json(name="text")
    val text: String
)
