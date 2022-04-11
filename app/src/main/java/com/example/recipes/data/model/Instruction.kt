package com.example.recipes.data.model

import com.squareup.moshi.Json

data class Instruction(
    @Json(name="type")
    val type: String?,
    @Json(name="text")
    val text: String
)
