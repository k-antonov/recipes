package com.example.recipes.data.entities

import com.squareup.moshi.Json

data class InstructionData(
    @Json(name="type")
    val type: String?,
    @Json(name="text")
    val text: String
)
