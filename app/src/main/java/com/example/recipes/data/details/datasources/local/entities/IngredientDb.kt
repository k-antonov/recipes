package com.example.recipes.data.details.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)