package com.example.recipes.data.categories.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.domain.categories.CategoryDomain

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(categoryDb: CategoryDb): Long

    @Query("SELECT id FROM categories WHERE name = :name")
    fun getIdByName(name: String): Long

    @Query("SELECT * FROM categories")
    fun selectAll(): List<CategoryDomain>
}