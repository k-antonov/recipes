package com.example.recipes.data.datasources.local

import android.content.Context

private const val DATABASE_NAME = "database.db"

// todo причесать код
class LocalDataSource(context: Context) {

    private val database = androidx.room.Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().build()

    fun getCategoryDao(): CategoryDao {
        return database.getCategoryDao()
    }

    fun getCuisineDao(): CuisineDao {
        return database.getCuisineDao()
    }

    fun getRecipeDao(): RecipeDao {
        return database.getRecipeDao()
    }
}