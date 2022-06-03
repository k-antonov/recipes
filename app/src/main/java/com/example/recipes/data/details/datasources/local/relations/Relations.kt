package com.example.recipes.data.details.datasources.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recipes.data.categories.datasources.local.CategoryDb
import com.example.recipes.data.cuisines.datasources.local.CuisineDb
import com.example.recipes.data.details.datasources.local.entities.IngredientDb
import com.example.recipes.data.details.datasources.local.entities.MeasureDb
import com.example.recipes.data.details.datasources.local.entities.RecipeDb
import com.example.recipes.data.details.datasources.local.entities.RecipesToIngredientsAndMeasures

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