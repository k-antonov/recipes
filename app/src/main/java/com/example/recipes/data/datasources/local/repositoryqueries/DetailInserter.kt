package com.example.recipes.data.datasources.local.repositoryqueries

import com.example.recipes.data.datasources.local.*
import com.example.recipes.domain.entities.DetailDomain

class DetailInserter(
    private val databaseSource: DatabaseSource
) {

    fun insertIfNotExists(detailDomain: DetailDomain) {
        if (databaseSource.getRecipeDao().isRecordExist(detailDomain.id.toLong()) == 0) {


            var categoryId =
                databaseSource.getCategoryDao().getIdByName(detailDomain.nameCategory)
            if (categoryId == 0L) {
                categoryId = databaseSource.getCategoryDao().insert(
                    CategoryDb(
                        name = detailDomain.nameCategory
                    )
                )
            }

            var cuisineId = databaseSource.getCuisineDao().getIdByName(detailDomain.nameCuisine)
            if (cuisineId == 0L) {
                cuisineId = databaseSource.getCuisineDao().insert(
                    CuisineDb(
                        name = detailDomain.nameCuisine
                    )
                )
            }

            databaseSource.getRecipeDao().insert(
                RecipeDb(
                    id = detailDomain.id.toLong(),
                    name = detailDomain.name,
                    categoryId = categoryId,
                    cuisineId = cuisineId,
                    instructions = detailDomain.strInstructions,
                    imageUrl = detailDomain.imageUrl
                )
            )

            for (pair in detailDomain.ingredients.zip(detailDomain.measures)) {
                var ingredientId =
                    databaseSource.getIngredientDao().getIdByName(pair.first)
                var measureId = databaseSource.getMeasureDao().getIdByName(pair.second)

                if (ingredientId == 0L) {
                    ingredientId = databaseSource.getIngredientDao().insert(
                        IngredientDb(
                            name = pair.first
                        )
                    )
                }

                if (measureId == 0L) {
                    measureId = databaseSource.getMeasureDao().insert(
                        MeasureDb(
                            name = pair.second
                        )
                    )
                }

                databaseSource.getRecipesToIngredientsAndMeasuresDao().insert(
                    RecipesToIngredientsAndMeasures(
                        recipeId = detailDomain.id.toLong(),
                        ingredientId = ingredientId,
                        measureId = measureId
                    )
                )
            }
        }
    }
}