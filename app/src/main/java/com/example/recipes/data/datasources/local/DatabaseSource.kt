package com.example.recipes.data.datasources.local

import android.content.Context

private const val DATABASE_NAME = "database.db"

class DatabaseSource(context: Context) {

    private val database = androidx.room.Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        DATABASE_NAME
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    fun getCategoryDao(): CategoryDao {
        return database.getCategoryDao()
    }

    fun getCuisineDao(): CuisineDao {
        return database.getCuisineDao()
    }

    fun getRecipeDao(): RecipeDao {
        return database.getRecipeDao()
    }

    fun getRecipesToIngredientsAndMeasuresDao(): RecipesToIngredientsAndMeasuresDao {
        return database.getRecipesToIngredientsAndMeasuresDao()
    }

    fun getIngredientDao(): IngredientDao {
        return database.getIngredientDao()
    }

    fun getMeasureDao(): MeasureDao {
        return database.getMeasureDao()
    }

    fun clearAllTables() {
        return database.clearAllTables()
    }

}