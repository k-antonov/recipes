package com.example.recipes.data.details.datasources.local.daos

import androidx.room.*
import com.example.recipes.data.details.datasources.local.relations.RecipeWithIngredientAndMeasureRelation
import com.example.recipes.data.details.datasources.local.entities.RecipesToIngredientsAndMeasures

@Dao
interface RecipesToIngredientsAndMeasuresDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures)

    @Transaction
    @Query("SELECT * FROM recipes_to_ingredients_and_measures " +
            "WHERE recipeId = :id " +
            "ORDER BY id")
    fun getIngredientsAndMeasuresById(id: Long): List<RecipeWithIngredientAndMeasureRelation>
}