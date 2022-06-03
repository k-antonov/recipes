package com.example.recipes.data.details.datasources.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "recipes_to_ingredients_and_measures",
    primaryKeys = ["recipeId", "ingredientId", "measureId"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeDb::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IngredientDb::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MeasureDb::class,
            parentColumns = ["id"],
            childColumns = ["measureId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class RecipesToIngredientsAndMeasures(
    val recipeId: Long,
    val ingredientId: Long,
    val measureId: Long
)