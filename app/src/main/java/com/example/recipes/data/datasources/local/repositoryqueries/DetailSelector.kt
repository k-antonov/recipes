package com.example.recipes.data.datasources.local.repositoryqueries

import com.example.recipes.data.datasources.local.DatabaseSource
import com.example.recipes.domain.entities.DetailDomain

class DetailSelector(
    private val databaseSource: DatabaseSource
) {

    fun select(id: Long): DetailDomain {
        val recipesTable = databaseSource.getRecipeDao().getDetailsById(id)
        val recipesToIngredientsAndMeasuresTable =
            databaseSource.getRecipesToIngredientsAndMeasuresDao()
                .getIngredientsAndMeasuresById(id)

        val ingredients = mutableListOf<String>()
        val measures = mutableListOf<String>()
        for (relation in recipesToIngredientsAndMeasuresTable) {
            ingredients.add(relation.ingredient.name)
            measures.add(relation.measure.name)
        }

        return DetailDomain(
            id = id.toString(),
            name = recipesTable.recipe.name,
            nameCategory = recipesTable.category.name,
            nameCuisine = recipesTable.cuisine.name,
            strInstructions = recipesTable.recipe.instructions,
            imageUrl = recipesTable.recipe.imageUrl,
            ingredients = ingredients,
            measures = measures
        )
    }
}