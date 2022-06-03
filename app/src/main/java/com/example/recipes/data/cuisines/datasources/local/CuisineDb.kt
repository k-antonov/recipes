package com.example.recipes.data.cuisines.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuisines")
data class CuisineDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String
)