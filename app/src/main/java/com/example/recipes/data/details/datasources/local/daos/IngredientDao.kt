package com.example.recipes.data.details.datasources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.details.datasources.local.entities.IngredientDb

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ingredientDb: IngredientDb): Long

    @Query("SELECT id FROM ingredients WHERE name = :name")
    fun getIdByName(name: String): Long
}