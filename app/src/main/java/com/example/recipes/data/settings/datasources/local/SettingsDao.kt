package com.example.recipes.data.settings.datasources.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SettingsDao {

    @Query("UPDATE sqlite_sequence SET seq = 0")
    fun clearSqliteSequence()
}