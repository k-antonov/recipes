package com.example.recipes.data.datasources.cloud.entities
import com.squareup.moshi.Json


data class CategoriesCloud(
    @Json(name = "categories")
    val categoriesCloud: List<CategoryCloud>
)

data class CategoryCloud(
    @Json(name = "idCategory")
    val id: String,
    @Json(name = "strCategory")
    val title: String,
    @Json(name = "strCategoryDescription")
    val description: String,
    @Json(name = "strCategoryThumb")
    val imageUrl: String
)