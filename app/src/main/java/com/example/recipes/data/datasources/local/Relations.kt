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

data class AllRecipeInfoRelation(
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
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "id",
        entity = RecipeDb::class
    )
    val recipeWithCategoryAndCuisineRelation: RecipeWithCategoryAndCuisineRelation
)

//data class RecipeWithIngredientAndMeasureRelation(
//    @Embedded val recipesToIngredientsAndMeasures: RecipesToIngredientsAndMeasures,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "ingredientId",
//        entity = IngredientDb::class
//    )
//    val ingredient: IngredientDb,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "measureId",
//        entity = MeasureDb::class
//    )
//    val measure: MeasureDb,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "recipeId",
//        entity = RecipeDb::class
//    )
//    val recipe: RecipeDb
//)


//data class CategoryWithRecipes(
//    @Embedded val category: CategoryDb,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "categoryId",
//        entity = RecipeDb::class
//    )
//    val recipes: List<RecipeDb>
//)
//
//data class CuisineAndCategoryWithRecipes(
//    @Embedded val cuisines: CuisineDb,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "cuisineId",
//        entity = RecipeDb::class
//    )
//    val categoriesWithRecipes: List<CategoryWithRecipes>
//)