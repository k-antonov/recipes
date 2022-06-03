package com.example.recipes.data.core.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipes.data.categories.datasources.local.CategoryDao
import com.example.recipes.data.categories.datasources.local.CategoryDb
import com.example.recipes.data.cuisines.datasources.local.CuisineDao
import com.example.recipes.data.cuisines.datasources.local.CuisineDb
import com.example.recipes.data.details.datasources.local.daos.IngredientDao
import com.example.recipes.data.details.datasources.local.daos.MeasureDao
import com.example.recipes.data.details.datasources.local.daos.RecipeDao
import com.example.recipes.data.details.datasources.local.daos.RecipesToIngredientsAndMeasuresDao
import com.example.recipes.data.details.datasources.local.entities.IngredientDb
import com.example.recipes.data.details.datasources.local.entities.MeasureDb
import com.example.recipes.data.details.datasources.local.entities.RecipeDb
import com.example.recipes.data.details.datasources.local.entities.RecipesToIngredientsAndMeasures

@Database(
    version = 5,
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