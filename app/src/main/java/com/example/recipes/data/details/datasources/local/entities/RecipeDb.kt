package com.example.recipes.data.details.datasources.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.recipes.data.categories.datasources.local.CategoryDb
import com.example.recipes.data.cuisines.datasources.local.CuisineDb

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryDb::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CuisineDb::class,
            parentColumns = ["id"],
            childColumns = ["cuisineId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class RecipeDb(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val categoryId: Long,
    val cuisineId: Long,
    val instructions: String,
    val imageUrl: String,
    var isFavorite: Boolean = false,
    val addedToFavoritesAt: Long? = null
)