package com.example.recipes.data.datasources.local

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithCategoryAndCuisineRelation(
    @Embedded val recipe: RecipeDb,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
        entity = CategoryDb::class
    )
    val category: CategoryDb,
    @Relation(
        parentColumn = "cuisineId",
        entityColumn = "id",
        entity = CuisineDb::class
    )
    val cuisine: CuisineDb
)

data class RecipeWithIngredientAndMeasureRelation(
    @Embedded val recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures,
    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "id",
        entity = IngredientDb::class
    )
    val ingredient: IngredientDb,
    @Relation(
        parentColumn = "measureId",
        entityColumn = "id",
        entity = MeasureDb::class
    )
    val measure: MeasureDb,
)