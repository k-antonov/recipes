package com.example.recipes.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 3,
    entities = [
        CategoryDb::class,
        CuisineDb::class,
        IngredientDb::class,
        MeasureDb::class,
        RecipeDb::class,
        RecipesToIngredientsAndMeasures::class
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getCuisineDao(): CuisineDao

    abstract fun getIngredientDao(): IngredientDao

    abstract fun getMeasureDao(): MeasureDao

    abstract fun getRecipeDao(): RecipeDao

    abstract fun getRecipesToIngredientsAndMeasuresDao(): RecipesToIngredientsAndMeasuresDao
}