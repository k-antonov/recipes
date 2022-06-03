package com.example.recipes.data.details.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measures")
data class MeasureDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)