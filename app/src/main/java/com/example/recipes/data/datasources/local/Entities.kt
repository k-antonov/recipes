package com.example.recipes.data.datasources.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryDb::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CuisineDb::class,
            parentColumns = ["id"],
            childColumns = ["cuisineId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
// todo возможно не нужны nullable, подумать
data class RecipeDb(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val name: String,
    val categoryId: Long? = null,
    val cuisineId: Long? = null,
    val instructions: String? = null,
    val imageUrl: String
)


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


@Entity(tableName = "categories")
data class CategoryDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String
)


@Entity(tableName = "cuisines")
data class CuisineDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val imageUrl: String
)


@Entity(tableName = "ingredients")
data class IngredientDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)


@Entity(tableName = "measures")
data class MeasureDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)