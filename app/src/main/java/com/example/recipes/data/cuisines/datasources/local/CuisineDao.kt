package com.example.recipes.data.cuisines.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.domain.cuisines.CuisineDomain

@Dao
interface CuisineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cuisineDb: CuisineDb): Long

    @Query("SELECT id FROM cuisines WHERE name = :name")
    fun getIdByName(name: String): Long

    @Query("SELECT * FROM cuisines")
    fun selectAll(): List<CuisineDomain>
}