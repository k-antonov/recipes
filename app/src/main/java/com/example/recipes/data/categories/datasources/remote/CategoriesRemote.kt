package com.example.recipes.data.categories.datasources.remote
import com.squareup.moshi.Json


data class CategoriesRemote(
    @Json(name = "categories")
    val categoriesRemote: List<CategoryRemote>
)

data class CategoryRemote(
    @Json(name = "idCategory")
    val id: String,
    @Json(name = "strCategory")
    val name: String,
    @Json(name = "strCategoryDescription")
    val description: String,
    @Json(name = "strCategoryThumb")
    val imageUrl: String
)