package com.example.recipes.data.details.datasources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipes.data.details.datasources.local.entities.MeasureDb

@Dao
interface MeasureDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(measureDb: MeasureDb): Long

    @Query("SELECT id FROM measures WHERE name = :name")
    fun getIdByName(name: String): Long
}